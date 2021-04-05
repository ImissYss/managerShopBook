package com.maidat.mybooks.controller;

import com.maidat.mybooks.domain.Book;
import com.maidat.mybooks.domain.Review;
import com.maidat.mybooks.domain.User;
import com.maidat.mybooks.domain.dto.ReviewDTO;
import com.maidat.mybooks.domain.repository.BookRepository;
import com.maidat.mybooks.domain.repository.ReviewRepository;
import com.maidat.mybooks.service.ReviewService;
import com.maidat.mybooks.utils.auth.AuthUser;
import com.maidat.mybooks.utils.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthUser authUser;

    @GetMapping("/reviews/{bookId}/add")
    public String addReview(@PathVariable("bookId") Long bookId,
                            Model model){

        Book book = bookRepository.findById(bookId).orElse(null);
        if(book != null){
            model.addAttribute("title", "Add new review to "+book.getTitle());
            model.addAttribute("review", new ReviewDTO());
            model.addAttribute("book", book);

            return "addReview";
        }

        return "404";

    }

    @RequestMapping(value = "/reviews/{bookId}/add", method = RequestMethod.POST)
    public String addReview(@PathVariable("bookId") Long bookId,
                            @ModelAttribute("review") @Valid ReviewDTO reviewDTO,
                            BindingResult result, HttpServletRequest req,
                            Model model){
        Book book = bookRepository.findById(bookId).orElse(null);

        if(book != null){
            Review review = null;
            model.addAttribute("added", false);
            model.addAttribute("book", book);

            if(!result.hasErrors()){
                review = reviewService.addReview(reviewDTO, book);
            }
            if(review != null){
                model.addAttribute("added", true);
                model.addAttribute("reviewPath", review.getId());
                model.addAttribute("reviewTitle", review.getTitle());
                model.addAttribute("bookId", book.getId());
                model.addAttribute("review", new ReviewDTO());
            }

            return "addReview";
        }
        return "404";
    }

    @GetMapping("/review/{reviewPath}")
    public String reviewPage(@PathVariable("reviewPath") Long reviewId,
                             Model model){

        Review review = reviewRepository.findById(reviewId).orElse(null);

        if(review == null)
            return "404";

        model.addAttribute("title", review.getTitle() + "| Review");
        model.addAttribute("review", review);

        reviewService.incrementViewCount(review);

        return "review";
    }

    @GetMapping("/reviews/edit/{reviewID}")
    public String editReview(@PathVariable("reviewID") Long reviewID,
                             Model model){

        if(!authUser.isLoggedIn())
            return "404";
        Review review = reviewRepository.findById(reviewID).orElse(null);
        UserInfo user = authUser.getUserInfo();

        if(review == null)
            return "404";

        if(user.getUser().getId() == review.getUser().getId() || user.isAdminOrModerator()){
            ReviewDTO editReview = reviewService.getReviewToEdit(review);

            model.addAttribute("title","Edit review: "+ review.getTitle());
            model.addAttribute("review", editReview);
            model.addAttribute("reviewTitle", editReview.getTitle());
            model.addAttribute("edit", true);

            return "addReview";
        }
        return "404";
    }

    @PostMapping("/reviews/edit/{reviewID}")
    public String editReview(@PathVariable("reviewID") Long reviewID,
                             @ModelAttribute("review") @Valid ReviewDTO reviewDTO,
                             BindingResult result,Model model){
        if(!authUser.isLoggedIn())
            return "404";

        Review review = reviewRepository.findById(reviewID).orElse(null);

        UserInfo userInfo = authUser.getUserInfo();

        if(review == null)
            return "404";

        if(userInfo.getUser().getId() == review.getUser().getId() || userInfo.isAdminOrModerator()){

            Review editReview = null;
            model.addAttribute("edited", false);
            model.addAttribute("edit", true);

            if(!result.hasErrors()){
                editReview = reviewService.editReview(reviewDTO, review);
            }
            if(editReview != null){
                model.addAttribute("edited", true);
                model.addAttribute("reviewPath", editReview.getId());
                model.addAttribute("reviewTitle", editReview.getTitle());
                model.addAttribute("review", new ReviewDTO());
            }
        }
        return "addReview";
    }

    @GetMapping("/reviews")
    public String listReviews(@PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(required = false) String sort,
                              @RequestParam(required = false) String size,
                              Model model){

        Page<Review> reviews = reviewRepository.findAll(pageable);
        int page = pageable.getPageNumber() + 1;
        int totalPage = reviews.getTotalPages();
        if(page > totalPage)
            return "404";

        model.addAttribute("title", "Reviews "+ page);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);

        return "reviews";

    }

    @GetMapping("/reviews/search")
    public String searchReview(@PageableDefault(size = 10, sort = "createDate",direction = Sort.Direction.DESC) Pageable pageable,
                               @RequestParam(required = false) String searchTerm,
                               Model model){

        if(searchTerm != null && !searchTerm.isEmpty()){

            Page<Review> reviews = reviewRepository.findAllByTitleIgnoreCaseContainingOrContentIgnoreCaseContaining(searchTerm, searchTerm, pageable);
            int page = pageable.getPageNumber() + 1;
            int totalPage = reviews.getTotalPages();

            model.addAttribute("title", "Search review: \"" + searchTerm +"\"|page " + page);
            model.addAttribute("reviews", reviews.getContent());
            model.addAttribute("page", page);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("search", true);
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("resultCount", reviews.getTotalElements());

        }else{
            model.addAttribute("title", "search review");
            model.addAttribute("search", false);
        }
        return "searchReviews";

    }
}
