package com.softeer.caart.domain.tag.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softeer.caart.domain.tag.service.TagService;
import com.softeer.caart.global.response.DataResponseDto;
import com.softeer.caart.global.response.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@Tag(name = "태그 API", description = "")
public class TagController {
	private final TagService tagService;

	@Operation(summary = "태그의 목록을 조회한다.", description = "태그는 priority를 기준으로 정렬한다. priority는 정렬에만 사용한다")
	@GetMapping
	public ResponseDto getTags() {
		return DataResponseDto.of("tags", tagService.getTags());
	}
}
