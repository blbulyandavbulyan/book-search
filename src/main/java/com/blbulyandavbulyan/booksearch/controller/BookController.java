package com.blbulyandavbulyan.booksearch.controller;

import com.blbulyandavbulyan.booksearch.service.index.IndexingService;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchResource;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book/")
public class BookController {
    private final BookSearchService bookSearchService;
    private final BookResponseMapper bookResponseMapper;
    private final IndexingService indexingService;

    @GetMapping("{id}")
    public BookResponse getBookById(@PathVariable("id") String id) {
        return bookSearchService.getBookById(id)
                .map(bookResponseMapper::toBookResponse)
                .orElseThrow(() -> Problem.builder()
                        .withTitle("Book not found")
                        .withStatus(Status.NOT_FOUND)
                        .withDetail("Book not found")
                        .with("id", id)
                        .build());
    }

    @PostMapping("search")
    public BookSearchResponse searchBooks(@RequestBody BookSearchRequest bookSearchRequest) {
        BookSearchResource bookSearchResource = bookSearchService.searchBooks(bookSearchRequest.toBookSearchResource());
        return bookResponseMapper.toBookSearchResponse(bookSearchResource);
    }

    @PostMapping("index-all")
    public MessageResponse indexAll(){
        indexingService.indexBooks();
        return new MessageResponse("Indexing is done successfully");
    }

    @GetMapping("suggest")
    public List<BookResponse> getSuggestedBooks(@RequestParam("query") String query){
        return bookSearchService.getSuggestedBooks(query)
                .stream()
                .map(bookResponseMapper::toBookResponse)
                .toList();
    }
}
