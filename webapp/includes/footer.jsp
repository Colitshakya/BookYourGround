<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<style>
.byg-site-footer {
    background: linear-gradient(135deg, #022f5b, #004d4d);
    color: #ffffff;
    padding: 60px 0 22px;
    margin-top: 60px;
}

.byg-footer-wrap {
    width: min(1400px, 92%);
    margin: 0 auto;
}

.byg-footer-top {
	display: grid;
    grid-template-columns: 1.3fr 1.4fr;
    gap: 60px;
    padding-bottom: 28px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.14);
}

.byg-footer-brand {
    max-width: 360px;
    margin-left: 80px;
}

.byg-footer-logo {
    display: inline-block;
    text-decoration: none;
    font-size: 28px;
    font-weight: 800;
    color: #ffffff;
    margin-bottom: 16px;
}

.byg-footer-logo span {
    color: #4ade80;
}

.byg-footer-brand p {
    margin: 0;
    font-size: 15px;
    line-height: 1.8;
    color: rgba(255, 255, 255, 0.85);
}

.byg-footer-links {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 40px;
}

.byg-footer-column h4 {
    margin: 0 0 16px;
    font-size: 18px;
    font-weight: 800;
    color: #ffffff;
}

.byg-footer-column a,
.byg-footer-column p {
    display: block;
    margin: 0 0 12px;
    text-decoration: none;
    font-size: 15px;
    line-height: 1.7;
    color: rgba(255, 255, 255, 0.84);
    transition: color 0.3s ease;
}

.byg-footer-column a:hover {
    color: #86efac;
}

.byg-footer-bottom {
    padding-top: 20px;
    text-align: center;
}

.byg-footer-bottom p {
    margin: 0;
    font-size: 14px;
    color: rgba(255, 255, 255, 0.72);
}

@media (max-width: 992px) {
    .byg-footer-top {
        grid-template-columns: 1fr;
    }

    .byg-footer-links {
        grid-template-columns: 1fr 1fr;
    }
}

@media (max-width: 768px) {
    .byg-site-footer {
        padding: 46px 0 20px;
    }

    .byg-footer-links {
        grid-template-columns: 1fr;
    }

    .byg-footer-logo {
        font-size: 24px;
    }
}
</style>
<footer class="byg-site-footer">
    <div class="byg-footer-wrap">

        <div class="byg-footer-top">
            <div class="byg-footer-brand">
                <a href="${pageContext.request.contextPath}/home" class="byg-footer-logo">
                    Book<span>YourGround</span>
                </a>
                <p>
                    Book Your Ground is your sports court booking platform for futsal,
                    basketball, tennis, and pickleball.
                </p>
            </div>

            <div class="byg-footer-links">
                <div class="byg-footer-column">
                    <h4>Quick Links</h4>
                    <a href="${pageContext.request.contextPath}/home">Home</a>
                    <a href="${pageContext.request.contextPath}/courts">Courts</a>
                    <a href="${pageContext.request.contextPath}/about">About Us</a>
                    <a href="${pageContext.request.contextPath}/contact">Contact</a>
                </div>

                <div class="byg-footer-column">
                    <h4>Contact</h4>
                    <p>Jhamsikhel, Lalitpur</p>
                    <p>+977 9821567256</p>
                    <p>support@bookyourground.com</p>
                </div>
            </div>
        </div>

        <div class="byg-footer-bottom">
            <p>© 2026 Book Your Ground. All rights reserved.</p>
        </div>

    </div>
</footer>