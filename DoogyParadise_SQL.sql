CREATE DATABASE `projectdb_dogggg`;

USE `projectdb_dogggg`;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `users` (
    `user_id` INT PRIMARY KEY AUTO_INCREMENT,
    `last_name` VARCHAR(50) NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `user_email` VARCHAR(255) NOT NULL UNIQUE,
    `user_password` VARCHAR(100),
    `user_gender` VARCHAR(10),
    `birth_date` datetime,
    `user_violation_count` INT DEFAULT 0,
    `last_login_time` datetime, -- 最近一次登入時間
    `user_img_path` VARCHAR(1500),
    `img_public_id` VARCHAR(500),
    `user_status` VARCHAR(20) -- 可根據實際需求使用合適的資料型態
);

create table `dog`(
`dog_id` INT PRIMARY KEY AUTO_INCREMENT,-- 流水號
`dog_name` VARCHAR(100),
`dog_img_path_local` VARCHAR(250),
`dog_img_path_cloud` VARCHAR(250),
`dog_img_public_id` VARCHAR(250),
`dog_gender` VARCHAR(10),
`dog_introduce` VARCHAR(250),
`dog_birth_date` datetime,
`dog_weight` double, -- 公斤
`dog_breed`  VARCHAR(100), -- 品種
`user_id` INT,
FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);



CREATE TABLE `credit_card`(
	`credit_id` INT primary key auto_increment,
	`user_id` INT ,-- 成員
    `card_number` VARCHAR(16),
    `card_CVC_code` VARCHAR(4),
    `card_expiry_year` INT,
    `card_expiry_month` INT,
    `card_holder_name` VARCHAR(100),
    `city` VARCHAR(10),             -- 縣市
    `district` VARCHAR(10),         -- 行政區
    `address` VARCHAR(50),          -- 住址
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `employee` (
    `employee_id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(20),
    `hire_date` datetime,
    `birth_date` datetime,
    `city` VARCHAR(10),             -- 縣市
    `district` VARCHAR(10),         -- 行政區
    `address` VARCHAR(50),          -- 住址
    `db_authority` VARCHAR(20) -- 員工對資料庫權限，這裡可以使用適合的資料型態
);

-- -------------------------------------------------------------------
CREATE TABLE `employee_permissions` (
  `permission_id` INT unique auto_increment, -- 權限表編號
  `department` VARCHAR(50) NOT NULL,
  `title` VARCHAR(20) NOT NULL,
  `db_authority` VARCHAR(20) PRIMARY KEY NOT NULL-- 員工對DB的權限類別
);
-- 增加外來鍵
ALTER TABLE `employee`
ADD FOREIGN KEY (`db_authority`) REFERENCES `employee_permissions`(`db_authority`);

INSERT INTO `employee_permissions` (
  `department`,
  `title`,
  `db_authority`

) VALUES
  ('人事部','主管','ROLE_HR1'),
 ('人事部','員工','ROLE_HR2'),
 ('營業部','主管','ROLE_C1'),
 ('營業部','員工','ROLE_C2'),
 ('貨運部','主管','ROLE_O1'),
 ('貨運部','員工','ROLE_O2'),
 ('營銷部','主管','ROLE_M1'),
 ('營銷部','員工','ROLE_M2'),
 ('客服部','主管','ROLE_S1'),
 ('客服部','員工','ROLE_S2')
 ;
 
 CREATE TABLE `shipping_company`(
	`shipping_company_id` INT PRIMARY KEY AUTO_INCREMENT,
	`shipping_company_name` VARCHAR(30) ,
	`shipping_company_freight` INT -- 物流價格
);

INSERT INTO `shipping_company` (
  `shipping_company_name`,
  `shipping_company_freight`
) VALUES
('711',60),
('全家',60),
('OK',60),
('萊爾富',60)
 ;
 
 CREATE TABLE `orders` (
    `order_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT, -- 買家
    `employee_id` INT, -- 哪個員工確認的
    `order_date` DATETIME,
    `total_price` INT, -- 原價
	`discount_price` INT, -- discount折扣多少錢
    `coupon_price` INT, -- coupon折扣多少錢
    `discount_description` VARCHAR(100),
	`coupon_description` VARCHAR(100),
    `payment_method` INT,  -- 付款方式 0:信用卡 1:貨到付款
    `payment_status` INT,  -- 狀態 0:未付款 1:已付款 2:已取消，未退3:完成退款，4:已取消，不須退款
    `confirm_date` DATETIME, -- 賣家確認訂單日期
    `ship_date` DATETIME, -- 賣家出貨日期
    `logistics_ship_date` DATETIME, -- 物流出貨日期
    `logistics_arrival_date` DATETIME, -- 物流到達日期
    `user_receive_date` DATETIME, -- 買家收貨日期
    `user_confirm_date` DATETIME, -- 買家確認日期
    `order_cancel_date` DATETIME, -- 取消日期
    `shipping_company_id` INT, -- 物流公司名字
    `city` VARCHAR(10),             -- 縣市
    `district` VARCHAR(10),         -- 行政區
    `address` VARCHAR(50),          -- 住址
    `freight` INT
);

-- 加外來键
ALTER TABLE `orders`
ADD FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`);

ALTER TABLE `orders`
ADD FOREIGN KEY (`shipping_company_id`) REFERENCES `shipping_company`(`shipping_company_id`);

ALTER TABLE `orders`
ADD FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`);

CREATE TABLE `order_detail` (
	`order_detail_id` INT PRIMARY KEY AUTO_INCREMENT,
    `order_id` INT,
    `product_id` INT,
    `quantity` INT,
    `price` INT,
    `discount` INT,
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
);

CREATE TABLE `product_category` (
    `category_id` INT PRIMARY KEY AUTO_INCREMENT,
    `category_name` VARCHAR(50) NOT NULL,
    `category_description` VARCHAR(100)
);




CREATE TABLE `product` (
    `product_id` INT PRIMARY KEY AUTO_INCREMENT,
    `product_name` VARCHAR(255),
    `employee_id` INT, -- 哪個員工上架的
    `unit_price` INT,
    `category_id` INT,
    `stock` INT,
    `reserved_quantity` INT,
    `listing_date` DATETIME, -- 上架日
    `modified_date` datetime,
    `product_description` TEXT,
	`discount_id` INT, -- 官方活動
	FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`),
    FOREIGN KEY (`category_id`) REFERENCES `product_category`(`category_id`)
	);



CREATE TABLE `product_gallery` (
    `product_id` INT,
    `img_id` INT PRIMARY KEY AUTO_INCREMENT,
    `img_path` VARCHAR(255),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
);

-- 雲端用
CREATE TABLE `product_gallery_cloud` (
    `product_id` INT,
    `cloud_id` INT PRIMARY KEY AUTO_INCREMENT,
    `cloud_path` VARCHAR(1000) NOT NULL,
    `cloud_public_id` VARCHAR(500),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
);

-- -------------------------------------------------------------------



CREATE TABLE `shopping_cart` (
	`shopping_cart_id` INT AUTO_INCREMENT,
    `user_id` INT,
    `product_id` INT,
    `quantity` INT,
    `created_time` datetime,
    `updated_time` datetime,
    PRIMARY KEY (`shopping_cart_id`)
);

-- 加外來键
ALTER TABLE `shopping_cart`
ADD FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`);

ALTER TABLE `shopping_cart`
ADD FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`);

-- 收藏功能
CREATE TABLE `collection`(
	`collection_id` INT PRIMARY KEY AUTO_INCREMENT,
    `collect` INT, -- 收藏(1是收藏,0是取消收藏)
    `user_id` INT,  
    `product_id` INT,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
);
-- 評論功能
CREATE TABLE `comment`(
	`comment_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT,  
    `product_id` INT,
    `star` int,
    `status` VARCHAR(50),
    `commentary` VARCHAR(300),
    `photo_path` VARCHAR(255),
    `post_date` DATETIME,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
);

-- 場地
CREATE TABLE venue (
 venue_id INT AUTO_INCREMENT PRIMARY KEY,
    venue_name varchar(50),
    venue_capacity_dog INT,
    venue_capacity_user INT,
    venue_description varchar(200),
    venue_rent INT
);

INSERT INTO venue
(`venue_name`,
`venue_capacity_dog`,
`venue_capacity_user`,
`venue_description`,
`venue_rent`)
VALUES
('室外陽光跑跳活動場',14,10,'戶外大空間，和狗狗們一起享受陽光與自由的跑跳',600),
('室內體能活動場',10,8,'專為狗狗設計的室內間，不怕天氣的限制',500);

-- 場地預約服務
CREATE TABLE venue_rental (
 rental_id INT AUTO_INCREMENT PRIMARY KEY, -- 預約號
    venue_id INT,
    user_id INT,-- 預約方
    participants_number INT, -- 預計參與人數
 dog_number INT, -- 預計參與狗數
    rental_date date, -- 租借日
    rental_start time, 
    rental_end time,
    rental_total INT, -- 總費用
    rental_order_status varchar(20) default '已訂',
    payment_status INT default 1,
    rental_order_date datetime,
    rental_update_date datetime,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`venue_id`) REFERENCES `venue`(`venue_id`)
);

-- 活動類型
CREATE TABLE activity_type (
 activity_type_id INT AUTO_INCREMENT PRIMARY KEY,
    activity_type_name varchar(50)
);
INSERT INTO `projectdb_dog`.`activity_type`
(`activity_type_name`)
VALUES
('寵保健'),('寵試吃'),('寵跑跳');



CREATE TABLE venue_activity (
 activity_id INT AUTO_INCREMENT PRIMARY KEY, -- 活動號
    activity_type_id INT,-- 活動類型
    venue_id INT,-- 活動室
    employee_id INT, -- 主辦
 activity_title varchar(80), -- 活動標題
    activity_date date, -- 活動日
    activity_start time, 
    activity_end time,
    activity_description text,-- 活動內容介紹
    activity_process varchar(1000), -- 活動流程++
    activity_notice varchar(1000), -- 活動注意事項++
    activity_listed_date datetime, -- 活動公布日
    activity_closing_date datetime, -- 報名截止日
    activity_update_date datetime, -- 活動更新日
    activity_status varchar(20) default '活動中',-- 1活動中 2.結束 3.取消
    activity_dog_number INT,-- 活動目標狗數
    current_dog_number INT,-- 現在狗數
    current_user_number INT,-- 現在人數
    activity_cost INT,-- 需不需要額外花費
    activity_cost_description varchar(200),-- 花費描述++
    contact_info varchar(20), -- 聯絡人
    contact_mail varchar(255),
    contact_phone varchar(20),
    FOREIGN KEY (`activity_type_id`) REFERENCES `activity_type`(`activity_type_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`),
    FOREIGN KEY (`venue_id`) REFERENCES `venue`(`venue_id`)
);

-- 活動附加說明照片
CREATE TABLE activity_gallery (
 gallery_id INT AUTO_INCREMENT PRIMARY KEY,
    activity_id INT,
    gallery_img_url varchar(500),
    gallery_public_id varchar(500),
    gallery_img_type varchar(20),
    FOREIGN KEY (`activity_id`) REFERENCES `venue_activity`(`activity_id`)
);


-- 使用者收藏活動
CREATE TABLE liked_activity (
 liked_activity_id INT AUTO_INCREMENT PRIMARY KEY,
    activity_id INT,
    user_id INT,
    liked_time datetime,
    FOREIGN KEY (`activity_id`) REFERENCES `venue_activity`(`activity_id`),
 FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);
-- 使用者評論活動
CREATE TABLE comment_activity (
 comment_id INT AUTO_INCREMENT PRIMARY KEY,
    activity_id INT,
    user_id INT,
    comment_text varchar(500),
    `score`INT,
    check_result varchar(10),
    comment_time datetime,
    FOREIGN KEY (`activity_id`) REFERENCES `venue_activity`(`activity_id`),
 FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);


CREATE TABLE activity_user_joined (
 user_joined_id INT AUTO_INCREMENT PRIMARY KEY,
    activity_id INT,
    user_id INT,
    joined_time datetime,
    user_note varchar(200),
    joined_status varchar(20) default '參加',
    update_time datetime,
    FOREIGN KEY (`activity_id`) REFERENCES `venue_activity`(`activity_id`),
 FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE activity_dog_joined (
 dog_joined_id INT AUTO_INCREMENT PRIMARY KEY,
    activity_id INT,
    dog_id INT,
    joined_time datetime,
    joined_status varchar(20) default '參加',
    update_time datetime,
    FOREIGN KEY (`activity_id`) REFERENCES `venue_activity`(`activity_id`),
 FOREIGN KEY (`dog_id`) REFERENCES `dog`(`dog_id`)
);

-- 推文
CREATE TABLE `tweet` (
    `tweet_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT,
    `user_name` VARCHAR(50),
	`tweet_content`VARCHAR(281),
    `pre_node` INT, -- 0為自己發的文，其他數字參考tweet_id，代表回覆哪則推文
    `post_date` datetime, -- 發文日期
    `tweet_status` INT, -- 發文狀態 0:刪文 1:一般狀態 2:檢舉過多，審核中 3:未通過
	`num_report` INT, -- 被檢舉的次數
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

-- 本地端圖片
CREATE TABLE `tweet_gallery`(
    `img_id` INT PRIMARY KEY AUTO_INCREMENT,
    `img_path` VARCHAR(255),
    `tweet_id` INT,
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`tweet_id`)
);

-- 雲端圖片
CREATE TABLE `tweet_gallery_cloud`(
    `img_id` INT PRIMARY KEY AUTO_INCREMENT,
    `img_path` VARCHAR(255),
    `tweet_id` INT,
    `cloud_public_id` VARCHAR(500),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`tweet_id`)
);

-- 追蹤
CREATE TABLE `tweet_follow_list`(
	`follow_list_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT,  -- 我
    `follwer_id`INT, -- 追蹤了誰
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`follwer_id`) REFERENCES `users`(`user_id`)
);

-- 按讚like
CREATE TABLE `tweet_like`(
	`tweet_like_id` INT PRIMARY KEY AUTO_INCREMENT,
    `tweet_id` INT, -- 哪則貼文
    `user_id` INT,  -- 被誰按讚
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`tweet_id`)
);

-- tag dog功能
CREATE TABLE `tweet_tag_dog`(
	`tweet_tag_dog` INT PRIMARY KEY AUTO_INCREMENT,
    `tweet_id` INT, -- 哪則貼文
    `dog_id` INT,  -- 被誰按讚
    FOREIGN KEY (`dog_id`) REFERENCES `dog`(`dog_id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`tweet_id`)
);

-- 通知
CREATE TABLE `tweet_notification`(
	`tweet_noti_id` INT PRIMARY KEY AUTO_INCREMENT,
    `post_time` datetime,
    `user_id` INT, -- 接收的user
    `content` VARCHAR(300),  -- 內文
    `tweet_id` INT,
    `is_read` INT, -- 0:未讀 1:已讀
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`tweet_id`)
);

-- 檢舉   我(reporter_id)檢舉，這則tweet(tweet_id)
CREATE TABLE `tweet_reports` (
    `reports_id` INT AUTO_INCREMENT PRIMARY KEY,
    `tweet_id` INT,
    `employee_id` INT, -- 哪個員工處裡的
    `reporter_id` INT,
    `report_reason` VARCHAR(255),
    `report_description` TEXT,
    `report_date` datetime DEFAULT CURRENT_TIMESTAMP,
    `report_status` INT , -- 0:未處裡 1:已處裡 ...
    `sexually_explicit` VARCHAR(20),
    `hate_speech` VARCHAR(20),
    `harassment`VARCHAR(20),
	`dangerous_content` VARCHAR(20),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`tweet_id`),
    FOREIGN KEY (`reporter_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`)
);

-- 官方推文
CREATE TABLE `tweet_official` (
    `tweet_id` INT PRIMARY KEY AUTO_INCREMENT,
    `employee_id` INT,
    `user_id` INT, -- 誰的留言
	`tweet_content`VARCHAR(281),
    `tweet_link`VARCHAR(400),
    `img_path_local` VARCHAR(255),
    `img_path_cloud` VARCHAR(800),
    `pre_node` INT, -- 0為自己發的文，其他數字參考tweet_id，代表回覆哪則推文
    `post_date` datetime, -- 發文日期
    `tweet_status` INT, -- 發文狀態 0:刪文 1:一般狀態 2:檢舉過多，審核中 3:未通過
	`num_report` INT, -- 被檢舉的次數
    FOREIGN KEY (`employee_id`) REFERENCES `employee`(`employee_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

-- 官方推文圖片
CREATE TABLE `tweet_official_gallery`(
    `img_id` INT PRIMARY KEY AUTO_INCREMENT,
    `img_path` VARCHAR(255),
    `tweet_id` INT,
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet_official`(`tweet_id`)
);

CREATE TABLE room (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_name INT,
    room_size INT,
    room_price INT,
    room_introduction VARCHAR(255),
    room_img_path VARCHAR(255)
);-- 插入room表的資料
INSERT INTO room (room_name, room_size, room_price, room_introduction, room_img_path)
VALUES 
(101, 1, 50, '1111', 'http://res.cloudinary.com/dxz9qtntt/image/upload/v1713237612/roomFolder/yo1zlj5sikzzes22zfdd.jpg'),
(102, 2, 60, '2222', 'https://res.cloudinary.com/dxz9qtntt/image/upload/f_auto,q_auto/v1/roomFolder/q5llpi4xghburz29n8wc'),
(103, 1, 70, '3333', 'https://res.cloudinary.com/dxz9qtntt/image/upload/f_auto,q_auto/v1/roomFolder/eduhz604i9lhyjbytfqe'),
(104, 3, 80, '4444', 'https://res.cloudinary.com/dxz9qtntt/image/upload/f_auto,q_auto/v1/roomFolder/ed29orld1lproohvz9oq'),
(105, 2, 90, '5555', 'https://res.cloudinary.com/dxz9qtntt/image/upload/f_auto,q_auto/v1/roomFolder/kcndxy6cxssavgdql1r8'),
(106, 1, 100, '6666', 'https://res.cloudinary.com/dxz9qtntt/image/upload/f_auto,q_auto/v1/roomFolder/zjeyn9wwjacwbe93xs3d'),
(107, 2, 110, '7777', 'https://res.cloudinary.com/dxz9qtntt/image/upload/f_auto,q_auto/v1/roomFolder/ccbfw3crqcksfzysh3tk'),
(108, 1, 120, '8888', 'https://res.cloudinary.com/dxz9qtntt/image/upload/f_auto,q_auto/v1/roomFolder/bkskjd2klzekbnupcayo'),
(109, 3, 130, '9999', 'https://res.cloudinary.com/dxz9qtntt/image/upload/f_auto,q_auto/v1/roomFolder/xe9jmghz6daogrnsdw9u'),
(110, 2, 140, '0000', 'https://res.cloudinary.com/dxz9qtntt/image/upload/f_auto,q_auto/v1/roomFolder/kcfoh49sq9243p6ejp9m');


SET FOREIGN_KEY_CHECKS = 1;