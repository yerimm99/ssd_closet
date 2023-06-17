package com.ssp.closet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssp.closet.dto.Meet;

public interface MeetRepository  extends JpaRepository<Meet, Integer>{
	Meet findByUserIdAndProductId(String userId, int productId);
	
	Integer getMeetCountByProductId(int productId);
	
}
