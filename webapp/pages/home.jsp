<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home - Book Your Ground</title>
<link rel="stylesheet" type="text/css" href= "${pageContext.request.contextPath}/css/home.css">
</head>
<body>
<%@ include file="/includes/header.jsp" %>

<section class="hero">
  <div class="container hero-grid">
    <div class="hero-text">
      <p class="eyebrow">MULTI-SPORT COURT BOOKING PLATFORM</p>
      <h1>Book courts for futsal, tennis, basketball and pickleball.</h1>
      <p class="hero-subtext">
        Discover grounds, compare prices, check time slots, and confirm your booking in minutes.
      </p>

      <div class="hero-actions">
        <a href="courts" class="btn btn-primary">Book Now</a>
        
      </div>

      
    </div>

   <div class="hero-image-card">
    <div class="stylish-hero-frame">
        <img src="${pageContext.request.contextPath}/photos/aerial-view-of-pickleball-courts.webp" alt="Sports Court" class="stylish-hero-image">
    </div>
</div>
  </div>
</section>

<section class="sports-section section-space">
  <div class="container">
    <div class="section-header">
      <p class="eyebrow">POPULAR SPORTS</p>
      <h2>Choose your game</h2>
    </div>

    <div class="card-grid four-grid">
      <a href="${pageContext.request.contextPath}/futsal">
      <div class="feature-card">
        <div class="icon-circle"><img src="https://imgs.search.brave.com/WYgJ8FYcu87lzG7VqIszQ4wde_CdPFtJ3tGgAQBVudU/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvMTE5/ODMxMzI5Ny9waG90/by9mb290YmFsbC1m/dXRzYWwtYmFsbC1n/b2FsLWFuZC1mbG9v/ci1pbmRvb3Itc29j/Y2VyLXNwb3J0cy1o/YWxsLXNwb3J0LWZ1/dHNhbC1iYWNrZ3Jv/dW5kLWluZG9vci5q/cGc_cz02MTJ4NjEy/Jnc9MCZrPTIwJmM9/OHdNUi1pWDZfSFFC/cVF5TkR0dUl5TXNn/NUdZb1JIYmRhMXYx/UC1UNVFpOD0" alt="football" class="sport-image"></div>
        <h3>Futsal</h3>
        <p>Indoor and outdoor futsal courts available.</p>
      </div>
      </a>
      <a href="${pageContext.request.contextPath}/basketball">
      <div class="feature-card">
        <div class="icon-circle"><img src="https://imgs.search.brave.com/mvDEqcB89w1_XSv0zrZwOf-UYWYT8OmS09X-wq2QZoY/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5nZXR0eWltYWdl/cy5jb20vaWQvMTE0/OTg3MDU0OS9waG90/by9zcG9ydC1pbWFn/ZS5qcGc_cz02MTJ4/NjEyJnc9MCZrPTIw/JmM9OGF0SEh2TnV5/WjJzd29GcHpWWUR1/azAwXzJlZVRxYm41/bDNHN2JtQzVHST0" alt="basketball" class="sport-image"></div>
        <h3>Basketball</h3>
        <p>Indoor and outdoor basketball courts available</p>
      </div>
      </a>
      <a href="${pageContext.request.contextPath}/tennis">
      <div class="feature-card">
        <div class="icon-circle"><img src="https://imgs.search.brave.com/5EqcRfMAb9wqJf9LfDtv1pNDB97fu5lsjLZRrNeyM38/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9wbHVz/LnVuc3BsYXNoLmNv/bS9wcmVtaXVtX3Bo/b3RvLTE3MDc0MjMz/ODkwNTAtNmEzOGE3/OGE3NTk3P2ZtPWpw/ZyZxPTYwJnc9MzAw/MCZhdXRvPWZvcm1h/dCZmaXQ9Y3JvcCZp/eGxpYj1yYi00LjEu/MCZpeGlkPU0zd3hN/akEzZkRCOE1IeHpa/V0Z5WTJoOE1UZDhm/SFJsYm01cGN5VXlN/SEpoWTJ0bGRIeGxi/bnd3Zkh3d2ZIeDhN/QT09" alt="tennis" class="sport-image"></div>
        <h3>Tennis</h3>
        <p>Outdoor tennis court available.</p>
      </div>
      </a>
      <a href="${pageContext.request.contextPath}/pickleball">
      <div class="feature-card">
        <div class="icon-circle"><img src="https://imgs.search.brave.com/A7j8ZxNGzWw3bpHVW_oSRS3tlOZmpmHu3iL8M24l_-o/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvMTcz/ODA4NzA5OS9waG90/by9hZHVsdHMtcGxh/eWluZy1waWNrbGVi/YWxsLW9uLWEtcHVi/bGljLWNvdXJ0Lmpw/Zz9zPTYxMng2MTIm/dz0wJms9MjAmYz1r/UmZNRVBjSldDUlFz/MUxUN29ydlRMaWNT/VDhQbDdCNUdpc3F2/VUpZR0JnPQ" alt="Book Your Ground Logo" class="sport-image">
        </div>
        <h3>Pickleball</h3>
        <p>Indoor and outdoor pickleball courts available.</p>
      </div>
      </a>
    </div>
  </div>
</section>

<section class="section-space light-bg">
  <div class="container">
    <div class="section-header">
      <p class="eyebrow">TRENDING COURTS</p>
      <h2>Popular bookings</h2>
    </div>

    <div class="card-grid three-grid">
    
       <div class="courts-card">
       <a href="${pageContext.request.contextPath}/courtDetails?court=turfpark-futsal">
                    <div class="courts-image-wrap">
                       <img src="${pageContext.request.contextPath}/photos/futsal_turfpark.webp" alt="Futsal Court">
                        <span class="courts-sport-tag">Futsal</span>
                    </div>

                    <div class="courts-body">
                        <div class="courts-card-top">
                            <h3>Turf Park Futsal</h3>
                        </div>

                        <p class="courts-location">⚲ Dholahiti, Kathmandu</p>
                        <p class="courts-description">
                            Premium indoor futsal court with bright lighting and quality turf.
                        </p>

                        <div class="courts-meta">
                            <div class="courts-meta-box">
                                <span>Price</span>
                                <strong>Rs. 1800/hr</strong>
                            </div>
                            <div class="courts-meta-box">
                                <span>Capacity</span>
                                <strong>10 Players</strong>
                            </div>
                        </div>

                        <div class="courts-actions">
                            <a href="${pageContext.request.contextPath}/courtDetails?court=turfpark-futsal" class="courts-btn-full">View & Book</a>
                        </div>
                    </div>
                    </a>
                </div>
				
			
		
      				<div class="courts-card">
      				<a href="${pageContext.request.contextPath}/courtDetails?court=turfpark-pickleball">
                    <div class="courts-image-wrap">
                        <img src="${pageContext.request.contextPath}/photos/pickelball_turfpark.webp" alt="Futsal Court">
                        <span class="courts-sport-tag">Pickleball</span>
                    </div>

                    <div class="courts-body">
                        <div class="courts-card-top">
                            <h3>Turf Park Pickleball Court</h3>
                            
                        </div>

                        <p class="courts-location">⚲ Jhamsikhel, Lalitpur</p>
                        <p class="courts-description">
                            Modern pickleball court for practice sessions and friendly matches.
                        </p>

                        <div class="courts-meta">
                            <div class="courts-meta-box">
                                <span>Price</span>
                                <strong>Rs. 1200/hr</strong>
                            </div>
                            <div class="courts-meta-box">
                                <span>Capacity</span>
                                <strong>4 Players</strong>
                            </div>
                        </div>

                        <div class="courts-actions">
                            <a href="${pageContext.request.contextPath}/courtDetails?court=turfpark-pickleball" class="courts-btn-full">View & Book</a>
                        </div>
                    </div>
                    </a>
                </div>
                

    		 <div class="courts-card">
     			<a href="${pageContext.request.contextPath}/courtDetails?court=picklepoint" >
                    <div class="courts-image-wrap">
                       <img src="${pageContext.request.contextPath}/photos/picklepoint.webp" alt="Futsal Court">
                        <span class="courts-sport-tag">Pickleball</span>
                    </div>

                    <div class="courts-body">
                        <div class="courts-card-top">
                            <h3>Picklepoint</h3>
                        </div>

                        <p class="courts-location">⚲ Pulchowk, Lalitpur</p>
                        <p class="courts-description">
                           Modern pickleball court for practice sessions and friendly matches.
                        </p>

                        <div class="courts-meta">
                            <div class="courts-meta-box">
                                <span>Price</span>
                                <strong>Rs. 1400/hr</strong>
                            </div>
                            <div class="courts-meta-box">
                                <span>Capacity</span>
                                <strong>4 Players</strong>
                            </div>
                        </div>

                        <div class="courts-actions">
                            <a href="${pageContext.request.contextPath}/courtDetails?court=picklepoint" class="courts-btn-full">View & Book</a>
                        </div>
                    </div>
                    </a>
                </div>
                <div class="hero-actions">
        			<a href="courts" class="btn btn-primary">Explore all ➜</a>
        
      				</div>

    </div>
  </div>
</section>
<section class="futsal-feature-section">
    <div class="futsal-feature-container">
        
        <div class="futsal-feature-left">
            <span class="futsal-feature-badge">BOOKING SYSTEM</span>

            <h2 class="futsal-feature-title">
                Professional Solutions <br>
                for <span>Sport Lovers</span>
            </h2>

            <p class="futsal-feature-text">
                Book Your Ground, Set the Match! Our seamless platform ensures
                your field is ready when you are.
            </p>

            <div class="futsal-feature-list">
                <div class="futsal-feature-item">
                    <div class="futsal-feature-icon">✓</div>
                    <p>User-friendly interface for effortless booking</p>
                </div>

                <div class="futsal-feature-item">
                    <div class="futsal-feature-icon">✓</div>
                    <p>Centralized calendar to manage all your matches in one place</p>
                </div>

                <div class="futsal-feature-item">
                    <div class="futsal-feature-icon">✓</div>
                    <p>Book your Ground with no hassle</p>
                </div>
            </div>
        </div>

        <div class="futsal-feature-right">
            <div class="futsal-feature-image-wrap">
                <img src="${pageContext.request.contextPath}/photos/sports.jpg" alt="sports">
            </div>
        </div>

    </div>
</section>

<%@ include file="/includes/footer.jsp" %>
</body>
</html>