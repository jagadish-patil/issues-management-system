package com.jp.issues_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jp.issues_management.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>{

	List<Issue> findByEmail(String email);
}
