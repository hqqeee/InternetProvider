/***********************************************************
* Insert initial(constant) data to database.
************************************************************/

USE `internet_provider`;

DELETE FROM `role` WHERE `id` IN (1,2);
INSERT INTO `role` VALUES
(1, 'Adminisrator', 'A user who can add/delete/edit tariff plans and register/block/unbloc Subscribers'),
(2, 'Subscriber', 'A user who pays to receive a service');

DELETE FROM `service` WHERE `id` BETWEEN 1 AND 4;
INSERT INTO `service` VALUES
(1,'Telephone', 'Calls(including voice, voicemail and conference and data calls) and supplementary services (including call forwarding and call transfer).'),
(2,'Internet', 'Service which provides a way for data to be transferred from Internet servers to your computer.'),
(3,'Cable TV', 'System of delivering television programming to consumers via radio frequency (RF) signals transmitted through coaxial cables.'),
(4,'IP-TV', 'Service which pprovides television programming and other video content using the Transmission Control Protocol/Internet Protocol (TCP/IP) suite.');

/* Create admin account */
DELETE FROM `user` WHERE `id` = 1;
INSERT INTO `user` (id,password,salt,login,role_id,blocked) VALUES
(1, 
'009139abfab87e55b55f6e2053823a265fb8dfa19b42eeae30de6321f362267f',
'79KRE7vmRGU=',
'admin',
1,
false
);
