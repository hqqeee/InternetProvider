USE internet_provider;
USE internet_provider;
INSERT INTO tariff (name, description, payment_period_days, rate, service_id)
VALUES
("Basic", "Includes a limited selection of popular channels and on-demand content, suitable for individuals or small households.", 14, 10,4),
("Family", "Offers a wider variety of channels, including children's programming and family-oriented movies and shows.", 14, 15,4),
("Sports Fan", "Includes a comprehensive selection of sports channels, covering a range of sports, including live games and highlights.", 28, 25,4),
("Movie Buff", "Features a vast library of movies and on-demand content, with new titles added regularly.", 28, 30,4),
("Premium", " A top-tier package that offers a wide range of channels, including premium movie channels and live sports events.", 28, 50,4),
("International", "Provides access to a selection of international channels, allowing you to stay connected with your home country.", 7, 20,4),
("News & Current Affairs", "Offers a variety of news channels, covering local, national and international events.", 1, 5,4),
("Music", "Features a range of music channels, including live concerts and music video channels.", 1, 2,4),
("Lifestyle", "Provides access to lifestyle and cultural channels, covering topics such as food, travel, and home improvement.", 14, 20,4),
("Complete", "An all-inclusive package that offers access to every channel and on-demand content available through the IP-TV service.", 28, 40,4),

("Essential", "A low-cost package that includes a selection of popular channels and local networks, ideal for individuals or small households.", 28, 15,3),
("Family", "Offers a wider variety of channels, including children's programming and family-oriented movies and shows.", 28, 20,3),
("Sports Fan", "Includes a comprehensive selection of sports channels, covering a range of sports, including live games and highlights.", 14, 15,3),
("Movie Buff", "Features a vast library of movies and on-demand content, with new titles added regularly.", 14, 20,3),
("Premium", "A top-tier package that offers a wide range of channels, including premium movie channels and live sports events.", 28, 40,3),
("News & Current Affairs", "Offers a variety of news channels, covering local, national and international events.", 28, 20,3),
("Music", "Features a range of music channels, including live concerts and music video channels.", 7, 10,3),
("Lifestyle", "Provides access to lifestyle and cultural channels, covering topics such as food, travel, and home improvement.", 7, 10,3),
("Complete", "An all-inclusive package that offers access to every channel and on-demand content available through the cable TV service.", 28, 45,3),
("International", "Provides access to a selection of international channels, allowing you to stay connected with your home country.", 14, 10,3),

("Daily", "A flexible daily billing option for those who prefer to pay for their internet service on a daily basis.", 1, 5,2),
("Weekly", "A weekly billing option for those who prefer to make payments every seven days.", 7, 20,2),
("Basic", "A low-cost package that provides a basic level of internet service, suitable for individuals or small households.", 28, 30,2),
("Premium", "A high-speed package that offers a fast and reliable internet connection, suitable for families, businesses, and heavy internet users.", 28, 60,2),
("Unlimited", "An unlimited data package that allows you to use the internet without worrying about data limits or overage charges.", 14, 50,2),
("Fiber", "A fiber-optic internet package that offers extremely fast speeds and a reliable connection, ideal for businesses and heavy internet users.", 14, 70,2),
("Mobile", "A mobile internet package that provides a wireless connection via a modem or hotspot, suitable for individuals who are always on the go.", 7, 20,2),
("Gaming", "A specialized package that provides a low-latency, high-speed connection, ideal for online gaming and streaming.", 7, 30,2),
("Streaming", "A package designed for heavy streaming and downloading, with a high-speed connection that can handle multiple devices at once.", 1, 10,2),
("Business", "A package designed for heavy streaming and downloading, with a high-speed connection that can handle multiple devices at once.", 7, 15,2),
("Hybrid", "A combination of fiber and cable internet, offering a fast and reliable connection with broad coverage.", 7, 10,2),
("Global", "A global roaming package that provides internet access while traveling abroad, with options for flexible data allowances.", 1, 10,2),


("Basic", "A low-cost package that provides a basic level of telephone service, suitable for individuals or small households.", 28, 10,1),
("Unlimited", "An unlimited calling package that allows you to make unlimited calls within a certain geographical area without incurring extra charges.", 28, 40,1),
("International", "An international calling package that allows you to make calls to a selection of international destinations at low rates.", 14, 30,1),
("Mobile", "A mobile telephone package that provides a wireless connection via a mobile phone, suitable for individuals who are always on the go.", 1, 10,1),
("Business", "A business-oriented package that provides a range of calling and messaging features, along with technical support and security features.", 7, 30,1),
("Virtual", "A virtual telephone package that provides a cloud-based phone system with advanced features, ideal for remote workers and small businesses.", 1, 20,1),
("Conference", "A package designed for hosting conference calls, with features such as call recording and participant management.", 14, 30,1),
("VoIP", "A Voice over Internet Protocol (VoIP) package that provides telephone service over the internet, with features such as call forwarding and voicemail.", 1, 7,1),
("Premium", "A high-end package that provides a wide range of calling and messaging features, with a high-quality connection.", 28, 29,1),
("Landline", "A traditional landline telephone package that provides a wired telephone connection, suitable for homes and businesses that require a stable connection.", 28, 10,1)


