<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<style>
.byg-site-header {
    width: 100%;
    background: #ffffff;
    border-bottom: 1px solid #e2e8f0;
    position: sticky;
    top: 0;
    z-index: 1000;
}

.byg-nav-wrap {
    width: min(1400px, 92%);
    margin: 0 auto;
    min-height: 92px;
    display: grid;
    grid-template-columns: 1fr auto 1fr;
    align-items: center;
    gap: 24px;
}

.byg-brand-area {
    display: flex;
    justify-content: flex-start;
    align-items: center;
}

.byg-logo {
    text-decoration: none;
    font-size: 28px;
    font-weight: 800;
    color: #0f172a;
    line-height: 1;
}

.byg-logo span {
    color: #16a34a;
}

.byg-nav {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 42px;
}

.byg-nav a {
    text-decoration: none;
    color: #334155;
    font-size: 16px;
    font-weight: 700;
    transition: color 0.3s ease;
}

.byg-nav a:hover {
    color: #16a34a;
}

.byg-nav-actions {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 14px;
}

.byg-btn,
.byg-btn-signup {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    padding: 14px 28px;
    border-radius: 999px;
    text-decoration: none;
    font-size: 15px;
    font-weight: 700;
    transition: all 0.3s ease;
}

.byg-btn-outline {
    border: 1px solid #d1d5db;
    background: #ffffff;
    color: #0f172a;
}

.byg-btn-outline:hover {
    border-color: #16a34a;
    color: #16a34a;
    background: #f0fdf4;
}

.byg-btn-signup {
    border: none;
    background: linear-gradient(135deg, #16a34a, #22c55e);
    color: #ffffff;
    box-shadow: 0 10px 22px rgba(34, 197, 94, 0.18);
}

.byg-btn-signup:hover {
    transform: translateY(-2px);
}

.byg-profile-circle {
    width: 48px;
    height: 48px;
    border-radius: 50%;
    overflow: hidden;
    border: 3px solid #cbd5e1;
    background: #ffffff;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    text-decoration: none;
    transition: all 0.3s ease;
    flex-shrink: 0;
    box-shadow: 0 2px 8px rgba(15, 23, 42, 0.04);
}

.byg-profile-circle:hover {
    border-color: #16a34a;
    background: #f0fdf4;
    transform: translateY(-1px);
}

.byg-profile-circle img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
}

.byg-profile-fallback {
    width: 100%;
    height: 100%;
    display: none;
    align-items: center;
    justify-content: center;
    font-size: 16px;
    font-weight: 800;
    color: #16a34a;
    background: #ecfdf5;
}

@media (max-width: 992px) {
    .byg-nav-wrap {
        grid-template-columns: 1fr auto 1fr;
        min-height: 84px;
    }

    .byg-nav {
        gap: 24px;
    }

    .byg-logo {
        font-size: 24px;
    }

    .byg-btn,
    .byg-btn-signup {
        padding: 12px 20px;
        font-size: 14px;
    }

    .byg-profile-circle {
        width: 46px;
        height: 46px;
    }
}

@media (max-width: 768px) {
    .byg-nav {
        display: none;
    }

    .byg-nav-wrap {
        grid-template-columns: 1fr 1fr;
    }

    .byg-brand-area {
        justify-content: flex-start;
    }

    .byg-nav-actions {
        justify-content: flex-end;
    }
}

@media (max-width: 576px) {
    .byg-nav-actions {
        gap: 10px;
    }

    .byg-btn,
    .byg-btn-signup {
        padding: 11px 16px;
        font-size: 13px;
    }

    .byg-logo {
        font-size: 22px;
    }

    .byg-profile-circle {
        width: 42px;
        height: 42px;
    }
}
</style>

<header class="byg-site-header">
    <div class="byg-nav-wrap">

        <div class="byg-brand-area">
            <a href="${pageContext.request.contextPath}/home" class="byg-logo">
                Book<span>YourGround</span>
            </a>
        </div>

        <nav class="byg-nav">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/courts">Courts</a>
            <a href="${pageContext.request.contextPath}/about">About Us</a>
            <a href="${pageContext.request.contextPath}/contact">Contact</a>
        </nav>

        <div class="byg-nav-actions">
            <a href="${pageContext.request.contextPath}/login"
               class="byg-btn byg-btn-outline"
               style="${empty sessionScope.loggedInUser ? 'display:inline-flex;' : 'display:none;'}">
               Log In
            </a>

            <a href="${pageContext.request.contextPath}/register"
               class="byg-btn-signup"
               style="${empty sessionScope.loggedInUser ? 'display:inline-flex;' : 'display:none;'}">
               Sign Up
            </a>

            <a href="${pageContext.request.contextPath}/userProfile"
               class="byg-profile-circle"
               style="${empty sessionScope.loggedInUser ? 'display:none;' : 'inline-flex;'}"
               title="Profile">

                <img src="${pageContext.request.contextPath}/getImage?name=${sessionScope.loggedInUser.userId}"
                     alt="Profile"
                     onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">

                <span class="byg-profile-fallback">
                    ${empty sessionScope.loggedInUser.firstName ? 'U' : sessionScope.loggedInUser.firstName.substring(0,1)}
                </span>
            </a>
        </div>

    </div>
</header>