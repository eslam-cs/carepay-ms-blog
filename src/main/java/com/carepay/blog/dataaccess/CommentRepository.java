package com.carepay.blog.dataaccess;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.carepay.blog.domain.CommentModel;
import com.carepay.blog.domain.PostModel;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long>, JpaSpecificationExecutor<CommentModel>{

	Optional<CommentModel> findByUuid(UUID uuid);
	List<CommentModel> findByPost(PostModel post,Pageable pageable);
	
	
}
