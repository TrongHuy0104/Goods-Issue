-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 15, 2024 lúc 10:34 AM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `exported`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `c_id` varchar(30) NOT NULL,
  `name_category` text NOT NULL,
  `status` tinyint(4) NOT NULL,
  `slug` text NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime NOT NULL,
  `parent_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee`
--

CREATE TABLE `employee` (
  `e_id` varchar(20) NOT NULL,
  `full_name` text NOT NULL,
  `address` text NOT NULL,
  `phone` varchar(191) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `image` text DEFAULT NULL,
  `age` smallint(6) NOT NULL DEFAULT 0,
  `gender` text NOT NULL,
  `username` varchar(256) NOT NULL,
  `password` text NOT NULL,
  `email` varchar(256) NOT NULL,
  `created_at` varchar(512) DEFAULT NULL,
  `updated_at` varchar(512) DEFAULT NULL,
  `isAdmin` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `issue-detail`
--

CREATE TABLE `issue-detail` (
  `id_id` varchar(30) NOT NULL,
  `i_id` varchar(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `p_id` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `issues`
--

CREATE TABLE `issues` (
  `i_id` varchar(20) NOT NULL,
  `u_id` varchar(30) NOT NULL,
  `i_date` varchar(450) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1,
  `s_id` varchar(30) NOT NULL,
  `e_id` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `p_id` varchar(30) NOT NULL,
  `name` text NOT NULL,
  `slug` text DEFAULT NULL,
  `code` varchar(256) NOT NULL,
  `rating` float NOT NULL DEFAULT 5,
  `banners` text DEFAULT NULL,
  `thumb` text DEFAULT NULL,
  `total_sale` bigint(20) DEFAULT NULL,
  `tags` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `s_id` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_category`
--

CREATE TABLE `product_category` (
  `pc_id` varchar(30) NOT NULL,
  `c_id` varchar(30) NOT NULL,
  `p_id` varchar(30) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_detail`
--

CREATE TABLE `product_detail` (
  `pd_id` varchar(30) NOT NULL,
  `p_id` varchar(30) NOT NULL,
  `size` int(11) DEFAULT NULL,
  `price` double NOT NULL,
  `description` text NOT NULL,
  `content` text DEFAULT NULL,
  `place_product` text NOT NULL,
  `use_expried_date` int(11) DEFAULT NULL,
  `date_manufacture` datetime DEFAULT NULL,
  `number_of_product` int(11) DEFAULT NULL,
  `number_left` int(11) NOT NULL COMMENT 'san pham con lai',
  `from_address` varchar(256) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 0,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `tax` int(11) DEFAULT 10
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `storage`
--

CREATE TABLE `storage` (
  `s_id` varchar(30) NOT NULL,
  `s_name` varchar(50) NOT NULL,
  `s_size` bigint(20) NOT NULL DEFAULT 0,
  `s_address` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `u_id` varchar(30) NOT NULL,
  `full_name` text NOT NULL,
  `address` text NOT NULL,
  `ship_address` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `phone` varchar(191) NOT NULL,
  `cmnd` varchar(256) DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `image` text DEFAULT NULL,
  `age` smallint(6) NOT NULL DEFAULT 0,
  `gender` text NOT NULL,
  `username` varchar(256) NOT NULL,
  `password` text NOT NULL,
  `email` varchar(256) NOT NULL,
  `created_at` varchar(512) DEFAULT NULL,
  `updated_at` varchar(512) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`c_id`);

--
-- Chỉ mục cho bảng `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`e_id`);

--
-- Chỉ mục cho bảng `issue-detail`
--
ALTER TABLE `issue-detail`
  ADD PRIMARY KEY (`id_id`),
  ADD UNIQUE KEY `i_id` (`i_id`,`p_id`),
  ADD KEY `p_id` (`p_id`);

--
-- Chỉ mục cho bảng `issues`
--
ALTER TABLE `issues`
  ADD PRIMARY KEY (`i_id`),
  ADD UNIQUE KEY `u_id` (`u_id`,`s_id`,`e_id`),
  ADD KEY `e_id` (`e_id`),
  ADD KEY `s_id` (`s_id`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`p_id`),
  ADD UNIQUE KEY `s_id` (`s_id`);

--
-- Chỉ mục cho bảng `product_category`
--
ALTER TABLE `product_category`
  ADD PRIMARY KEY (`pc_id`),
  ADD KEY `c_id` (`c_id`),
  ADD KEY `p_id` (`p_id`);

--
-- Chỉ mục cho bảng `product_detail`
--
ALTER TABLE `product_detail`
  ADD PRIMARY KEY (`pd_id`),
  ADD UNIQUE KEY `p_id` (`p_id`);

--
-- Chỉ mục cho bảng `storage`
--
ALTER TABLE `storage`
  ADD PRIMARY KEY (`s_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`u_id`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `issue-detail`
--
ALTER TABLE `issue-detail`
  ADD CONSTRAINT `issue-detail_ibfk_1` FOREIGN KEY (`i_id`) REFERENCES `issues` (`i_id`),
  ADD CONSTRAINT `issue-detail_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `products` (`p_id`);

--
-- Các ràng buộc cho bảng `issues`
--
ALTER TABLE `issues`
  ADD CONSTRAINT `issues_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `users` (`u_id`),
  ADD CONSTRAINT `issues_ibfk_2` FOREIGN KEY (`e_id`) REFERENCES `employee` (`e_id`),
  ADD CONSTRAINT `issues_ibfk_3` FOREIGN KEY (`s_id`) REFERENCES `storage` (`s_id`);

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`s_id`) REFERENCES `storage` (`s_id`);

--
-- Các ràng buộc cho bảng `product_category`
--
ALTER TABLE `product_category`
  ADD CONSTRAINT `product_category_ibfk_1` FOREIGN KEY (`c_id`) REFERENCES `categories` (`c_id`),
  ADD CONSTRAINT `product_category_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `products` (`p_id`);

--
-- Các ràng buộc cho bảng `product_detail`
--
ALTER TABLE `product_detail`
  ADD CONSTRAINT `product_detail_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `products` (`p_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
