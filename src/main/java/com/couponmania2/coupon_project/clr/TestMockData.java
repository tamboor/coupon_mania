package com.couponmania2.coupon_project.clr;

import com.couponmania2.coupon_project.beans.*;
import com.couponmania2.coupon_project.facade.AdminServiceImpl;
import com.couponmania2.coupon_project.facade.CustomerServiceImpl;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.couponmania2.coupon_project.repositories.CouponRepo;
import com.couponmania2.coupon_project.repositories.CustomerRepo;
import com.couponmania2.coupon_project.repositories.PurchaseRepo;
import com.couponmania2.coupon_project.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1)
public class TestMockData implements CommandLineRunner {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CouponRepo couponRepo;
    @Autowired
    private PurchaseRepo purchaseRepo;
    @Autowired
    private CustomerServiceImpl customerFacade;

    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    CustomerServiceImpl customerService;


    @Override
    public void run(String... args) throws Exception {


        companyRepo.save(new Company("Sick Adventures", "sick@adventures.com", "sickpass"));
        companyRepo.save(new Company("Rocky Power", "rocky@power.com", "rockypass"));
        companyRepo.save(new Company("Mystic Life", "mystic@life.com", "mysticpass"));
        companyRepo.save(new Company("All In", "all@in.com", "allpass"));
        companyRepo.save(new Company("Viva La Vida", "viva@lavida.com", "vivapass"));


        couponRepo.save(new Coupon(companyRepo.getById(1L), Category.food, "Sick Hamburger",
                "220g of pure beef", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://recipe-graphics.grocerywebsite.com/0_GraphicsRecipes/4589_4k.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(1L), Category.xtreme, "Sky Dive",
                "Extreme sky jump", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20),"https://www.skydivecsc.com/hs-fs/hubfs/how-long-does-a-tandem-skydive-last.jpg?width=999&name=how-long-does-a-tandem-skydive-last.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(1L), Category.tattoos, "B&G tattoo",
                "Classic black & grey ink", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                    (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://images.squarespace-cdn.com/content/v1/5ce1f0d2d711f3000103fe5f/1619112902185-0D61XWP5SKQ0QJRZSR1S/IMG_0585.PNG"));
        couponRepo.save(new Coupon(companyRepo.getById(1L), Category.cars, "BMW",
                "Rent a BMW for 3 days", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://www.bmwgroup.com/content/dam/grpw/websites/bmwgroup_com/responsibility/720x720_Nachhaltigkeit.jpg.grp-transform/small/720x720_Nachhaltigkeit.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(1L), Category.vacation, "Ibiza",
                "10% discount at every hotel", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://zone1-ibizaspotlightsl.netdna-ssl.com/sites/default/files/styles/auto_1500_width/public/article-images/124541/slideshow-1576662714.jpg"));

        couponRepo.save(new Coupon(companyRepo.getById(2L), Category.vacation, "Rocky Trail",
                "15% discount on trial registration", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://www.rockymtnresorts.com/wp-content/uploads/2018/12/manhikinginmountains_950_636.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(2L), Category.cars, "Paint Job",
                "Custom paint your ride!", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://cfx-wp-images.imgix.net/2021/12/Car-Paint-Protection.jpg?auto=compress%2Cformat&ixlib=php-3.3.0&s=13dede2e133d90a45deb9eac6ac9d4c5"));
        couponRepo.save(new Coupon(companyRepo.getById(2L), Category.tattoos, "Random Tattoo",
                "Random Tattoo by one of our best artists", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://cdn.shopify.com/s/files/1/1176/9992/products/random-set-temporary-tattoo-474.jpg?v=1599052686"));
        couponRepo.save(new Coupon(companyRepo.getById(2L), Category.xtreme, "Wakeboard lesson",
                "Discover this amazing sport with us", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://monstertower.com/pub/media/mageplaza/blog/post/j/a/jack-van-tricht-izwepu8aqbc-unsplash.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(2L), Category.food, "Pizza Party",
                "50% discount in every pizza joint", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://media.istockphoto.com/photos/different-tipes-of-pizza-picture-id1297246932?b=1&k=20&m=1297246932&s=170667a&w=0&h=dk_4yOLKIGZzjCtWwT0pg2Xy8QCEoV9PHNPXCgvbCdQ="));

        couponRepo.save(new Coupon(companyRepo.getById(3L), Category.vacation, "Desert Flower",
                "Weekend at the majestic Judea desert", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "http://www.holyjerusalemtours.com/asp/en/wp-content/uploads/2017/02/des3.png"));
        couponRepo.save(new Coupon(companyRepo.getById(3L), Category.cars, "Flying carpet",
                "Who needs a car when you can get a flying carpet!", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://upload.wikimedia.org/wikipedia/commons/5/55/Vasnetsov_samolet.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(3L), Category.tattoos, "Star Sign tattoo",
                "Tattoo your star sign with us", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://ourmindfullife.com/wp-content/uploads/2019/04/Unique-Taurus-zodiac-tattoos-OurMindfulLife.com-4.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(3L), Category.xtreme, "Bungee Jump",
                "20% off your next 5 jumps", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://media-cdn.tripadvisor.com/media/photo-s/19/22/f4/f5/bungee-jump-70-meters.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(3L), Category.food, "Vegan Cooking Course",
                "Learn how to cook vegan delights", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://yeastextract.info/wp-content/uploads/2018/09/Vegetarian-and-vegan-cuisine.jpg"));

        couponRepo.save(new Coupon(companyRepo.getById(4L), Category.vacation, "Las Vegas",
                "10% discount at every hotel", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://cdn.getyourguide.com/img/location/5ffeb496e3e09.jpeg/88.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(4L), Category.cars, "Limo Ride",
                "Day long Limousine rental", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://club-vip.net/wp-content/uploads/2018/11/hompage-limo-sm.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(4L), Category.tattoos, "Ace of Spades",
                "Tattoo the best card", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://www.savedtattoo.com/wp-content/uploads/2021/10/Artsy-Black-Ink-Ace-Tattoo.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(4L), Category.xtreme, "Poker Marathon",
                "24hr long poker marathon", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://worldfinancialreview.com/wp-content/uploads/2021/04/flush-royal-in-poker-player-hand-lucky-winner-stockpack-adobe-stock-scaled-1.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(4L), Category.food, "French Fries",
                "10 french fries meals", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://i1.wp.com/www.awortheyread.com/wp-content/uploads/2020/07/French-Fry-Grazing-Board-9.jpg"));

        couponRepo.save(new Coupon(companyRepo.getById(5L), Category.vacation, "London Calling",
                "30% discount at every hotel", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://media.ticmate.com//resources/ticmate_live/upload_go/9e062c852a50939e-LondonEyeMobile4.jpg"));
        couponRepo.save(new Coupon(companyRepo.getById(5L), Category.cars, "Pimp My Ride",
                "10% discount at your local garage", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://www.motortrend.com/uploads/sites/21/2020/09/001-chuck-fisher-garage.jpg?fit=around%7C875:492"));
        couponRepo.save(new Coupon(companyRepo.getById(5L), Category.tattoos, "Free Tattoo",
                "1+1 on all tattoos", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://i0.wp.com/www.society19.com/uk/wp-content/uploads/sites/5/2018/03/best-friend-tattoo-ideas-e1521821541561.jpg?fit=800%2C492&ssl=1"));
        couponRepo.save(new Coupon(companyRepo.getById(5L), Category.xtreme, "Scuba Dive",
                "Explore the deep with us", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://i0.wp.com/www.touristisrael.com/wp-content/uploads/2010/10/shutterstock_294652889.jpg?fit=1200%2C800&ssl=1"));
        couponRepo.save(new Coupon(companyRepo.getById(5L), Category.food, "Sushi Bomb",
                "50 sushi rolls", DateUtils.getRandomSqlStartDate(), DateUtils.getRandomSqlEndDate(),
                (int)(Math.random()*20+1), (int)(Math.random()*481+20), "https://www.eatthis.com/wp-content/uploads/sites/4/2020/07/assorted-sushi.jpg?quality=82&strip=1"));




        customerRepo.save(new Customer("nir", "katz", "nir@katz.com", "nirpass"));
        customerRepo.save(new Customer("alon", "mintz", "alon@mintz.com", "alonpass"));
        customerRepo.save(new Customer("ran", "manor", "ran@manor.com", "ranpass"));
        customerRepo.save(new Customer("yael", "katz", "yael@katz.com", "yaelpass"));
        customerRepo.save(new Customer("barak", "hamdani", "barak@hamdani.com", "barakpass"));
        customerRepo.save(new Customer("tal", "rozner", "tal@rozner.com", "talpss"));
        customerRepo.save(new Customer("zeev", "mindali", "zeev@mindali.com", "zeevpass"));
        customerRepo.save(new Customer("itzik", "siman tov", "itzik@simantov.com", "itzikpass"));

        purchaseRepo.save(new Purchase(customerRepo.getById(1L), couponRepo.getById(3L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(1L), couponRepo.getById(4L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(2L), couponRepo.getById(5L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(2L), couponRepo.getById(6L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(3L), couponRepo.getById(3L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(3L), couponRepo.getById(5L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(4L), couponRepo.getById(11L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(4L), couponRepo.getById(23L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(5L), couponRepo.getById(2L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(5L), couponRepo.getById(17L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(6L), couponRepo.getById(18L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(6L), couponRepo.getById(9L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(7L), couponRepo.getById(10L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(7L), couponRepo.getById(21L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(8L), couponRepo.getById(24L)));
        purchaseRepo.save(new Purchase(customerRepo.getById(8L), couponRepo.getById(16L)));



    }
}
