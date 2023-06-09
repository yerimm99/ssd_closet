package com.ssp.closet.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;


import com.ssp.closet.dto.Account;
import com.ssp.closet.dto.Auction;
import com.ssp.closet.dto.Bid;
import com.ssp.closet.dto.Groupbuy;
import com.ssp.closet.dto.LikeMark;
import com.ssp.closet.dto.Meet;
import com.ssp.closet.dto.Delivery;
import com.ssp.closet.dto.Product;
import com.ssp.closet.dto.Review;
import com.ssp.closet.repository.AccountRepository;
import com.ssp.closet.repository.AuctionRepository;
import com.ssp.closet.repository.BidRepository;
import com.ssp.closet.repository.DeliveryRepository;
import com.ssp.closet.repository.GroupbuyRepository;
import com.ssp.closet.repository.LikeMarkRepository;
import com.ssp.closet.repository.MeetRepository;
import com.ssp.closet.repository.ProductRepository;
import com.ssp.closet.repository.ReviewRepository;

@Service
@Transactional
public class ClosetImpl implements ClosetFacade{

	//계정
	@Autowired
	private AccountRepository accountRepository;

	public Account getAccount(String userId) {
		return accountRepository.findByUserId(userId);
	}

	public Account getAccount(String userId, String password) {
		return accountRepository.findByUserIdAndPassword(userId, password);
	}

	public void createAccount(Account account) {
		accountRepository.save(account);
	}
	
	@Autowired  
	private ProductRepository productRepository;

	public Product getProduct(int productId) {
		return productRepository.findByProductId(productId);
	}


	//경매
	@Autowired
	private AuctionRepository aucRepository;

	public void insertAuction(Auction auction) {
		aucRepository.save(auction);
	}

	public Auction getAuction(int productId) {
		return aucRepository.findByProductId(productId); 
	}

	public void updateMaxPrice(int productId) {
		aucRepository.updatePrice(productId, findMaxPrice(productId).getBidPrice());
	}

	public void deleteAuctionByProductId(int productId) {
		aucRepository.deleteByProductId(productId);
	}

	public List<Auction> getAuctionByCategoryId(String categoryId) {
		return aucRepository.findByCategoryIdOrderByStatusDescRegisterDateDesc(categoryId);
	}

	public List<Auction> searchAuctionList(String keywords) {
		return aucRepository.findByNameIgnoreCaseContainingOrderByStatusDescRegisterDateDesc(keywords);
	}

	public List<Auction> getAuctionByUsed(int used){
		return aucRepository.findByUsedOrderByStatusDescRegisterDateDesc(used);
	}

	public List<Auction> getAuctionByCategoryIdAndUsed(String categoryId, int used){
		return aucRepository.findByCategoryIdAndUsedOrderByRegisterDateDesc(categoryId, used);
	}

	public List<Auction> findSellAuctionByAccount(Account account){
		return aucRepository.findByAccountOrderByRegisterDateDesc(account);
	}

	public List<Auction> findTop4AuctionOrderByRegisterDate(){
		return aucRepository.findTop4OrderByRegisterDate();
	}

	public List<Auction> getAuctionList() {
		return aucRepository.findAllByOrderByStatusDescRegisterDateDesc();
	}

	@Autowired
	private TaskScheduler scheduler;

	public void scheduleAuctionEnd(Auction auction) { //낙찰처리

		Date closingTime = auction.getEndDate(); // 경매 종료 시간을 가져옴
		Runnable auctionEndTask = new Runnable() {
			@Override
			public void run() {
				Bid highestBid = findMaxPrice(auction.getProductId());
				if (highestBid != null && auction.getStatus() != 0) {
					// 낙찰 처리
					auction.setWinner(highestBid.getUserId());
					updateResult(auction.getWinner(), auction.getProductId());
				}
				auction.setStatus(0);
				aucRepository.save(auction);
			}
		};

		// 스케줄 생성: closingTime에 auctionEndTask 실행

		scheduler.schedule(auctionEndTask, closingTime);
		System.out.println("Auction end task has been scheduled to execute at " + closingTime);
	}

	//   @Scheduled(cron = "10 * * * * *") // 경매 종료 확인 주기
	//    public void checkAuctionEnd() {
	//        // 경매 종료 시간이 현재 시간보다 이전인 경매 조회
	//        List<Auction> endedAuctions = aucRepository.findEndedAuctions(LocalDateTime.now());
	//        
	//        // 각 경매에 대해 최고 입찰가 확인
	//        for (Auction auction : endedAuctions) {
	//            Bid highestBid = findMaxPrice(auction.getProductId());
	//            if (highestBid != null) {
	//                // 낙찰 처리
	//                auction.setWinner(highestBid.getUserId());
	//                updateResult(auction.getWinner());
	//            }
	//            auction.setStatus(0);
	//            aucRepository.save(auction);
	//        }
	//    }

	//수동 낙찰
	public void closedAuctionBySupp(Auction auction) {
		auction.setStatus(0);
		Bid highestBid = findMaxPrice(auction.getProductId());
		if (highestBid != null) {
			// 낙찰 처리
			auction.setWinner(highestBid.getUserId());
			updateResult(auction.getWinner(), auction.getProductId());
		}
		aucRepository.save(auction);
	}


	//입찰
	@Autowired
	private BidRepository bidRepository;

	public void createBid(Bid bid) {
		bidRepository.save(bid);
		updateMaxPrice(bid.getProductId());
	}

	public boolean isBidPriceExists(int productId, int bidPrice) {
		return bidRepository.existsByProductIdAndBidPrice(productId, bidPrice);
	}

	public void deleteBid(int productId, String userId) {
		bidRepository.deleteByProductIdAndUserId(productId, userId);
	}

	public Integer countBidByProductId(int productId) {
		return bidRepository.countByProductId(productId);
	}

	public void updateResult(String userId, int productId) {
		bidRepository.updateSuccessResult(userId, productId);
		bidRepository.updateFailResult(userId, productId);
	}

	public Bid findMaxPrice(int productId) {
		return bidRepository.findTopByProductIdOrderByBidPriceDesc(productId);
	}    

	public List<Bid> getBid(String userId) {
		return bidRepository.findByUserId(userId);
	}

	public Bid getBid(String userId, int productId) {
		return bidRepository.findByUserIdAndProductId(userId, productId);
	}

	//공동구매
	@Autowired
	private GroupbuyRepository groupbuyRepository;

	public void insertGroupbuy(Groupbuy groupbuy) {
		groupbuyRepository.save(groupbuy);
	}

	public List<Groupbuy> getGroupbuyByCategoryId(String categoryId) {
		return groupbuyRepository.findByCategoryIdOrderByStatusDescRegisterDateDesc(categoryId);
	}

	public Groupbuy getGroupbuyDetail(int productId) {
		return groupbuyRepository.getReferenceById(productId); 
	}

	public void deleteGroupbuyByProductId(int productId) {
		groupbuyRepository.deleteByProductId(productId);
	}

	public List<Groupbuy> findSellGroupbuyByAccount(Account account){
		return groupbuyRepository.findByAccountOrderByStatusDescRegisterDateDesc(account);
	}

	public Groupbuy findBuyGroupbuyByProductId(int productId){
		return groupbuyRepository.findByProductId(productId);
	}

	public List<Groupbuy> searchGroupbuyList(String keywords) {
		return groupbuyRepository.findByNameIgnoreCaseContainingOrderByStatusDescRegisterDateDesc(keywords);
	}   
	public List<Groupbuy> getGroupbuyList() {
		return groupbuyRepository.findAllByOrderByStatusDescRegisterDateDesc();
	}

	public void scheduleGroupbuyEnd (Groupbuy groupbuy) { //실패처리
		Date closingTime = groupbuy.getEndDate(); // 공동구매 종료 시간을 가져옴
		System.out.println(closingTime);
		Runnable groupbuyEndTask = new Runnable() {
			@Override
			public void run() {
				groupbuy.setStatus(0);
				groupbuyRepository.save(groupbuy);
				List<Meet> meet = meetRepository.findByProductId(groupbuy.getProductId());
				for(Meet m : meet){
					m.setMeetResult(2);
					meetRepository.save(m);
				}
			}
		};

		// 스케줄 생성: closingTime에 groupbuyEndTask 실행
		scheduler.schedule(groupbuyEndTask, closingTime);
		System.out.println("Groupbuy end task has been scheduled to execute at " + closingTime);
	}

	public List<Groupbuy> findTop4GroupbuyOrderByRegisterDate(){
		return groupbuyRepository.findTop4OrderByRegisterDate();
	}

	//공구참여
	@Autowired
	private MeetRepository meetRepository;

	public void insertMeet(Meet meet) {
		meetRepository.save(meet);
	}

	public Meet findMeetByUserIdAndProductId(String userId, int productId) {
		return meetRepository.findByUserIdAndProductId(userId, productId);
	}

	public List<Meet> findMeetByProductId(int productId){
		return meetRepository.findByProductId(productId);
	}

	public List<Meet> findMeetByUserId(String userId){
		return meetRepository.findByUserId(userId);
	}

	public Integer getMeetCountByProductId(int productId) {
		return meetRepository.getMeetCountByProductId(productId);
	}

	public void deleteByUserIdAndProductId(String userId, int productId) {
		meetRepository.deleteByUserIdAndProductId(userId, productId);
	}


	//주문
	@Autowired
	private DeliveryRepository deliveryRepository;

	public void createDelivery(Delivery delivery) {
		deliveryRepository.save(delivery);
	}

	public List<Delivery> getOrderList(String userId) {
		return deliveryRepository.findAllByUserId(userId);
	}

	public Delivery getOrder(int orderId) {
		return deliveryRepository.findByOrderId(orderId);
	}

	public Delivery getOrderByUserIdAndProductId(String userId, int productId) {
		return deliveryRepository.findByUserIdAndProductId(userId, productId);
	}

	//리뷰
	@Autowired
	private ReviewRepository reviewRepository;

	public void insertReview(Review review) {
		reviewRepository.save(review);
	}

	public List<Review> readReviewListToMe(String userId) {
		return reviewRepository.findByUserId(userId);
	}
	public Review findReviewByOrderId(int orderId) {
		return reviewRepository.findByOrderId(orderId);
	}

	//판매자 평점 평균
	public String userRating(String userId) {
		List<Float> ratings = reviewRepository.getRatingsByUserId(userId);
		if (ratings != null && !ratings.isEmpty()) {
			double sum = 0.0;
			for (Float rating : ratings) {
				sum += rating;
			}
			double average = sum / ratings.size();
			return String.format("%.1f", average);
		} else {
			return "";
		}
	}


	//좋아요
	@Autowired
	private LikeMarkRepository likeRepository;

	public void createLike(LikeMark like) {
		likeRepository.save(like);
	}
	public void deleteLike(Product product, Account account) {
		likeRepository.deleteByProductAndAccount(product, account);
	}
	public List<LikeMark> findLikeMark(Account account) {
		return likeRepository.findByAccount(account);
	}

	public Integer getLikeSum(int productId) {
		return likeRepository.getMarkSumByProduct(productId);
	}

	public LikeMark cheakLikeMark(Product product, Account account) {
		return likeRepository.findByProductAndAccount(product, account);
	}

	//랭킹 좋아요순
	public List<Auction> getAuctionSortedByLikeCount() {
		List<Auction> products = aucRepository.findAll();

		for (Auction product : products) {
			if(getLikeSum(product.getProductId()) != null) {
				int likeCount = getLikeSum(product.getProductId());
				product.setLikeCount(likeCount);
			}
		}

		products.sort(Comparator.comparingInt(Product::getLikeCount).reversed());

		return products;
	}
	public List<Groupbuy> getGroupbuySortedByLikeCount() {
		List<Groupbuy> products = groupbuyRepository.findAll();

		for (Groupbuy product : products) {
			if(getLikeSum(product.getProductId()) != null) {
				int likeCount = getLikeSum(product.getProductId());
				product.setLikeCount(likeCount);
			}
		}

		products.sort(Comparator.comparingInt(Product::getLikeCount).reversed());

		return products;
	}


	//랭킹 평점순
	public List<Auction> getAuctionRankingByReviewRating() {
		List<Account> sellers = accountRepository.findAll();

		for (Account seller : sellers) {
			if(userRating(seller.getUserId()) != "") {
				double rating = Double.parseDouble(userRating(seller.getUserId()));
				seller.setAvgRating(rating);
			}
		}

		sellers.sort(Comparator.comparingDouble(Account::getAvgRating).reversed());

		List<Auction> rankingProducts = new ArrayList<>();

		for (Account seller : sellers) {
			List<Auction> sellerProducts = findSellAuctionByAccount(seller);
			rankingProducts.addAll(sellerProducts);
			if (rankingProducts.size() >= 10) {
				break;
			}
		}

		return rankingProducts.subList(0, Math.min(rankingProducts.size(), 10));
	}

	public List<Groupbuy> getGroupbuyRankingByReviewRating() {
		List<Account> sellers = accountRepository.findAll();

		for (Account seller : sellers) {
			if(userRating(seller.getUserId()) != "") {
				double rating = Double.parseDouble(userRating(seller.getUserId()));
				seller.setAvgRating(rating);
			}
		}

		sellers.sort(Comparator.comparingDouble(Account::getAvgRating).reversed());

		List<Groupbuy> rankingProducts = new ArrayList<>();

		for (Account seller : sellers) {
			List<Groupbuy> sellerProducts = findSellGroupbuyByAccount(seller);
			rankingProducts.addAll(sellerProducts);
			if (rankingProducts.size() >= 10) {
				break;
			}
		}

		return rankingProducts.subList(0, Math.min(rankingProducts.size(), 10));
	}




}