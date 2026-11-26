<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FASHION HUB - Gợi Ý Phối Đồ Phù Hợp Với Bạn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Playfair+Display:wght@500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --bg-cream: #F6F3EC;
            --bg-sidebar: #EFE7DA;
            --card-bg: #FCFBF9;
            --text-dark: #1E1E1E;
            --text-muted: #5C5852;
            --btn-dark: #262626;
        }
        body { background-color: var(--bg-cream); color: var(--text-dark); font-family: 'Inter', sans-serif; }
        .navbar-custom { background-color: var(--bg-cream); padding: 18px 0; border-bottom: 1px solid #E5DFD3; }
        .brand-logo { font-weight: 600; letter-spacing: 1.5px; line-height: 1.1; font-size: 1.05rem; color: var(--text-dark); text-decoration: none; text-align: center; }
        .nav-menu-link { color: var(--text-dark); font-weight: 500; font-size: 0.95rem; text-decoration: none; padding: 6px 14px; }
        .nav-menu-link:hover { opacity: 0.65; }
        .nav-icon-btn { color: var(--text-dark); font-size: 1.25rem; text-decoration: none; }

        /* Sidebar Bộ lọc hàng dọc bên trái */
        .filter-sidebar { background-color: var(--bg-sidebar); border-radius: 18px; padding: 22px; position: sticky; top: 20px; }
        .filter-group-title { font-size: 0.82rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.6px; color: var(--text-dark); margin-bottom: 8px; margin-top: 16px; }
        .filter-group-title:first-child { margin-top: 0; }
        .filter-select { background-color: #FCFBF9; border: 1px solid #DFD5C5; border-radius: 10px; padding: 10px 12px; font-size: 0.9rem; color: var(--text-dark); }
        .cat-pill-list { display: flex; flex-direction: column; gap: 6px; }
        .cat-radio-label { display: flex; align-items: center; gap: 8px; padding: 8px 12px; border-radius: 8px; background-color: rgba(255,255,255,0.65); cursor: pointer; font-size: 0.9rem; transition: all 0.15s; }
        .cat-radio-label:hover { background-color: #fff; }
        .cat-radio-label input:checked + span { font-weight: 700; color: #000; }

        .btn-suggest { background-color: var(--btn-dark); color: #FFFFFF; border: none; border-radius: 10px; padding: 11px; font-weight: 500; font-size: 0.95rem; }
        .btn-suggest:hover { background-color: #000; color: #fff; }

        /* Thanh danh mục con nằm ngang phía trên sản phẩm */
        .subcat-bar {
            background-color: #FCFBF9;
            border: 1px solid #E5DFD3;
            border-radius: 14px;
            padding: 12px 16px;
            margin-bottom: 22px;
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            gap: 8px;
        }
        .subcat-chip {
            padding: 6px 16px;
            border-radius: 20px;
            font-size: 0.88rem;
            font-weight: 500;
            text-decoration: none;
            color: var(--text-dark);
            background-color: #EFE7DA;
            transition: all 0.2s;
        }
        .subcat-chip:hover { background-color: #DFD2BE; color: #000; }
        .subcat-chip.active { background-color: var(--btn-dark); color: #FFFFFF; }

        /* Lookbook Card */
        .lookbook-card { background-color: var(--card-bg); border: 1px solid #E5DFD3; border-radius: 20px; padding: 20px; height: 100%; display: flex; flex-direction: column; transition: transform 0.25s ease, box-shadow 0.25s ease; }
        .lookbook-card:hover { transform: translateY(-4px); box-shadow: 0 12px 28px rgba(0, 0, 0, 0.06); }
        .lookbook-title { font-family: 'Playfair Display', serif; font-size: 1.4rem; font-weight: 500; margin-bottom: 14px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
        .lookbook-img-box { position: relative; border-radius: 14px; overflow: hidden; height: 340px; background-color: #EAE4D8; margin-bottom: 16px; }
        .lookbook-img { width: 100%; height: 100%; object-fit: cover; }

        .floating-tag { position: absolute; background: rgba(255, 255, 255, 0.95); backdrop-filter: blur(4px); border-radius: 10px; padding: 5px 9px; display: flex; align-items: center; gap: 7px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12); text-decoration: none; color: var(--text-dark); max-width: 155px; }
        .floating-tag img { width: 32px; height: 32px; border-radius: 6px; object-fit: cover; }
        .floating-tag-info { line-height: 1.2; overflow: hidden; }
        .floating-tag-name { font-size: 0.7rem; font-weight: 600; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
        .floating-tag-price { font-size: 0.7rem; color: #555; }
        .tag-pos-0 { top: 18%; right: 10px; }
        .tag-pos-1 { bottom: 24%; left: 10px; }
        .tag-pos-2 { bottom: 10px; right: 10px; }

        .btn-buy-dark { background-color: var(--btn-dark); color: #fff; border-radius: 10px; padding: 10px; font-size: 0.9rem; font-weight: 500; text-decoration: none; text-align: center; display: block; margin-top: auto; }
        .btn-buy-dark:hover { opacity: 0.88; color: #fff; }
    </style>
</head>
<body>

<nav class="navbar-custom">
    <div class="container d-flex justify-content-between align-items-center">
        <a href="home" class="brand-logo">FASHION<br>HUB</a>
        <div class="d-none d-md-flex align-items-center gap-3">
            <a href="home" class="nav-menu-link">Trang chủ</a>
            <a href="home?season=Thu" class="nav-menu-link">Xu hướng</a>
            <a href="home" class="nav-menu-link">Bộ sưu tập</a>
            <a href="#about" class="nav-menu-link">Về chúng tôi</a>
        </div>
        <div class="d-flex align-items-center gap-3">
            <a href="saved-outfits" class="nav-icon-btn" title="Bộ đồ đã lưu"><i class="bi bi-heart"></i></a>
            <a href="login" class="nav-icon-btn" title="Tài khoản"><i class="bi bi-person"></i></a>
        </div>
    </div>
</nav>

<div class="container py-4">
    <div class="text-center mb-4">
        <h2 class="fw-bold mb-1">GỢI Ý PHỐI ĐỒ PHÙ HỢP VỚI BẠN</h2>
        <p class="text-muted">Lọc theo danh mục trang phục, dáng người và phong cách của bạn.</p>
    </div>

    <div class="row g-4">
        <!-- CỘT TRÁI: THANH LỌC DANH MỤC HÀNG DỌC -->
        <div class="col-lg-3">
            <aside class="filter-sidebar shadow-sm">
                <form id="filterForm" action="home" method="get">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <span class="fw-bold fs-6"><i class="bi bi-sliders2 me-1"></i> BỘ LỌC</span>
                        <a href="home" class="small text-muted text-decoration-underline">Xóa lọc</a>
                    </div>

                    <!-- 1. Danh mục món đồ cha -->
                    <div class="filter-group-title">Danh mục món đồ</div>
                    <div class="cat-pill-list">
                        <label class="cat-radio-label">
                            <input type="radio" name="categoryId" value="0" ${selectedCategory == 0 ? 'checked' : ''} onchange="this.form.submit()">
                            <span>Tất cả món đồ</span>
                        </label>
                        <c:forEach items="${parentCategories}" var="c">
                            <label class="cat-radio-label">
                                <input type="radio" name="categoryId" value="${c.categoryId}" ${selectedCategory == c.categoryId ? 'checked' : ''} onchange="this.form.submit()">
                                <span>${c.name}</span>
                            </label>
                        </c:forEach>
                    </div>

                    <!-- 2. Dáng người -->
                    <div class="filter-group-title">Dáng người</div>
                    <select name="bodyTypeId" class="form-select filter-select">
                        <option value="0">-- Tất cả dáng người --</option>
                        <c:forEach items="${bodyTypes}" var="b">
                            <option value="${b.bodyTypeId}" ${selectedBodyType == b.bodyTypeId ? 'selected' : ''}>${b.name}</option>
                        </c:forEach>
                    </select>

                    <!-- 3. Phong cách -->
                    <div class="filter-group-title">Phong cách</div>
                    <select name="styleId" class="form-select filter-select">
                        <option value="0">-- Tất cả phong cách --</option>
                        <c:forEach items="${styles}" var="s">
                            <option value="${s.styleId}" ${selectedStyle == s.styleId ? 'selected' : ''}>${s.name}</option>
                        </c:forEach>
                    </select>

                    <!-- 4. Mùa -->
                    <div class="filter-group-title">Mùa trong năm</div>
                    <select name="season" class="form-select filter-select">
                        <option value="ALL">-- Tất cả các mùa --</option>
                        <option value="Xuân" ${selectedSeason == 'Xuân' ? 'selected' : ''}>Xuân</option>
                        <option value="Hạ" ${selectedSeason == 'Hạ' ? 'selected' : ''}>Hạ</option>
                        <option value="Thu" ${selectedSeason == 'Thu' ? 'selected' : ''}>Thu</option>
                        <option value="Đông" ${selectedSeason == 'Đông' ? 'selected' : ''}>Đông</option>
                        <option value="Bốn mùa" ${selectedSeason == 'Bốn mùa' ? 'selected' : ''}>Bốn mùa</option>
                    </select>

                    <!-- 5. Dịp mặc -->
                    <div class="filter-group-title">Dịp sử dụng</div>
                    <select name="occasion" class="form-select filter-select mb-4">
                        <option value="ALL">-- Tất cả dịp --</option>
                        <c:forEach items="${occasions}" var="occ">
                            <option value="${occ}" ${selectedOccasion == occ ? 'selected' : ''}>${occ}</option>
                        </c:forEach>
                    </select>

                    <div class="d-grid">
                        <button type="submit" class="btn btn-suggest">Áp dụng bộ lọc</button>
                    </div>
                </form>
            </aside>
        </div>

        <!-- CỘT PHẢI: THANH DANH MỤC CON NGANG + LƯỚI HIỂN THỊ CÁC SET ĐỒ -->
        <div class="col-lg-9">

            <!-- THANH DANH MỤC CON (Chỉ hiện khi danh mục cha được chọn có danh mục con) -->
            <c:if test="${not empty subCategories}">
                <div class="subcat-bar shadow-sm">
                    <span class="small fw-bold text-muted me-1"><i class="bi bi-tags"></i> Phân loại chi tiết:</span>

                    <a href="home?categoryId=${selectedCategory}&subCategoryId=0&bodyTypeId=${selectedBodyType}&styleId=${selectedStyle}&season=${selectedSeason}&occasion=${selectedOccasion}"
                       class="subcat-chip ${selectedSubCategory == 0 ? 'active' : ''}">
                        Tất cả
                    </a>

                    <c:forEach items="${subCategories}" var="sub">
                        <a href="home?categoryId=${selectedCategory}&subCategoryId=${sub.categoryId}&bodyTypeId=${selectedBodyType}&styleId=${selectedStyle}&season=${selectedSeason}&occasion=${selectedOccasion}"
                           class="subcat-chip ${selectedSubCategory == sub.categoryId ? 'active' : ''}">
                                ${sub.name}
                        </a>
                    </c:forEach>
                </div>
            </c:if>

            <!-- LƯỚI CÁC THẺ OUTFIT -->
            <div class="row g-4">
                <c:if test="${empty outfits}">
                    <div class="col-12 text-center py-5 bg-white rounded-4 border">
                        <p class="text-muted fs-5 mb-2">Chưa tìm thấy set đồ nào khớp với bộ lọc hiện tại.</p>
                        <a href="home" class="btn btn-outline-dark btn-sm">Xem tất cả gợi ý</a>
                    </div>
                </c:if>

                <c:forEach items="${outfits}" var="o">
                    <div class="col-md-6 col-xl-4">
                        <div class="lookbook-card">
                            <h3 class="lookbook-title" title="${o.title}">${o.title}</h3>
                            <div class="lookbook-img-box">
                                <img src="${o.imageUrl}" alt="${o.title}" class="lookbook-img"
                                     onerror="this.src='https://placehold.co/400x520/E5D7C3/262626?text=Fashion+Lookbook'">

                                <c:forEach items="${o.products}" var="p" varStatus="loop">
                                    <c:if test="${loop.index < 3}">
                                        <a href="${pageContext.request.contextPath}/mua-san-pham?id=${p.productId}" target="_blank" class="floating-tag tag-pos-${loop.index}" title="Click để mua ${p.name}">
                                            <img src="${p.imageUrl}" alt="${p.name}" onerror="this.src='https://placehold.co/60x60/eee/333?text=Item'">
                                            <div class="floating-tag-info">
                                                <div class="floating-tag-name">${p.name}</div>
                                                <div class="floating-tag-price">
                                                    <fmt:formatNumber value="${p.price}" type="number" maxFractionDigits="0"/>đ
                                                </div>
                                            </div>
                                        </a>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <a href="outfit-detail?id=${o.outfitId}" class="btn-buy-dark">
                                Mua ngay <i class="bi bi-link-45deg"></i>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>