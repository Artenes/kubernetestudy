package io.github.artenes.anotes;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Dependencies {

    @Bean
    public Parser markdownParser() {
        return Parser.builder().build();
    }

    @Bean
    public HtmlRenderer markdownRenderer() {
        return HtmlRenderer.builder().build();
    }

}
