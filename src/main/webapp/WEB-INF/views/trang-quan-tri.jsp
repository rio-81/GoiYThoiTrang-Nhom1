<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="muc" value="${param.muc}" />

<c:choose>
    <c:when test="${not empty param.maSetSua}">
        <c:set var="muc" value="set-do" />
    </c:when>
    <c:when test="${not empty param.maSanPhamSua}">
        <c:set var="muc" value="san-pham" />
    </c:when>
    <c:when test="${muc ne 'set-do' and muc ne 'san-pham' and muc ne 'danh-muc' and muc ne 'thong-ke'}">
        <c:set var="muc" value="tong-quan" />
    </c:when>
</c:choose>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang quản trị</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- Khung và menu dùng chung cho năm mục quản trị. --%>
    <style>
        body { margin: 0; font-family: Arial, sans-serif; background: #f6f3ee; color: #211e1b; }
        .khung-quan-tri { display: grid; grid-template-columns: 220px minmax(0, 1fr); min-height: 100vh; }
        .menu-quan-tri { padding: 12px 8px; color: #faf7f1; background: linear-gradient(110deg, #292624, #22201e 62%, #302c28); }
        .thuong-hieu { margin: 0 0 30px; text-align: center; }
        .thuong-hieu strong { font: 750 23px/1.2 Arial, sans-serif; letter-spacing: -.6px; }
        .thuong-hieu small { margin-top: 9px; display: flex; align-items: center; justify-content: center; gap: 8px; color: #d8c3a7; font: 600 10px Arial, sans-serif; letter-spacing: 3px; }
        .thuong-hieu small::before, .thuong-hieu small::after { content: ""; width: 24px; height: 1px; background: #958671; }
        .menu-quan-tri nav { display: grid; gap: 9px; }
        .menu-quan-tri nav > a, .menu-quan-tri nav > span { display: flex; align-items: center; gap: 16px; box-sizing: border-box; min-height: 44px; padding: 9px 14px; color: inherit; border-radius: 8px; font: 15px/1.3 Arial, sans-serif; text-decoration: none; }
        .menu-quan-tri nav svg { flex: 0 0 22px; width: 22px; height: 22px; fill: none; stroke: currentColor; stroke-width: 1.65; stroke-linecap: round; stroke-linejoin: round; }
        .menu-quan-tri nav a:hover { background: #48413a; }
        .menu-quan-tri nav a[aria-current="page"] { background: #76695b; color: white; }
        .menu-quan-tri nav a:focus-visible { outline: 2px solid #e4c9a5; outline-offset: -2px; }
        .menu-quan-tri nav hr { width: calc(100% - 24px); height: 1px; border: 0; background: #71675b; margin: 10px auto 7px; }
        .cot-noi-dung { min-width: 0; }
        .thanh-tren { display: flex; align-items: center; justify-content: space-between; padding: 16px 24px; background: white; border-bottom: 1px solid #e6dfd4; }
        .noi-dung-quan-tri { padding: 20px 24px; overflow-x: auto; }
        @media (max-width: 700px) {
            .khung-quan-tri { grid-template-columns: minmax(0, 1fr); }
            .menu-quan-tri { padding: 10px 12px; }
            .menu-quan-tri .thuong-hieu { margin: 0 0 8px; }
            .menu-quan-tri nav { display: flex; overflow-x: auto; }
            .menu-quan-tri nav hr { display: none; }
            .menu-quan-tri nav > * { flex: 0 0 auto; }
            .noi-dung-quan-tri { padding: 14px; }
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/giao-dien-${muc}.css">
</head>
<body>

    <div class="khung-quan-tri">
        <aside class="menu-quan-tri">
            <div class="thuong-hieu">
                <strong>FASHION HUB</strong>
                <small>ADMIN PANEL</small>
            </div>

           <c:set var="duongDanQuanTri" value="${pageContext.request.contextPath}/quan-tri" />

           <nav aria-label="Menu quản trị">
               <a href="${duongDanQuanTri}"
                  aria-current="${muc eq 'tong-quan' ? 'page' : 'false'}"><svg aria-hidden="true" viewBox="0 0 24 24"><path d="M2 11 12 2l10 9 M5 9v12h5v-7h4v7h5V9"/></svg>Tổng quan</a>

               <a href="${duongDanQuanTri}?muc=set-do"
                  aria-current="${muc eq 'set-do' ? 'page' : 'false'}"><svg aria-hidden="true" viewBox="0 0 24 24"><path d="M2 7l10-5 10 5-10 5z M3 12l9 5 9-5 M3 17l9 5 9-5"/></svg>Quản lý Set đồ</a>

               <a href="${duongDanQuanTri}?muc=san-pham"
                  aria-current="${muc eq 'san-pham' ? 'page' : 'false'}"><svg aria-hidden="true" viewBox="0 0 24 24"><path d="M5 7h14l2 14H3z M9 9V6a3 3 0 0 1 6 0v3"/></svg>Quản lý sản phẩm</a>

               <a href="${duongDanQuanTri}?muc=danh-muc"
                  aria-current="${muc eq 'danh-muc' ? 'page' : 'false'}"><svg aria-hidden="true" viewBox="0 0 24 24"><path d="M3 13 14 2h7v8L10 21z M17 6h.1"/></svg>Quản lý danh mục</a>

               <a href="${duongDanQuanTri}?muc=thong-ke"
                  aria-current="${muc eq 'thong-ke' ? 'page' : 'false'}"><svg aria-hidden="true" viewBox="0 0 24 24"><path d="M5 20v-6 M12 20V8 M19 20V3"/></svg>Thống kê Affiliate</a>

               <hr>
               <a href="${pageContext.request.contextPath}/home"><svg aria-hidden="true" viewBox="0 0 24 24"><path d="M2 11 12 2l10 9 M5 9v12h5v-7h4v7h5V9"/></svg>Về trang chủ</a>
               <span><svg aria-hidden="true" viewBox="0 0 24 24"><path d="M11 3H4v18h7 M9 12h13 M17 7l5 5-5 5"/></svg>Đăng xuất</span>
           </nav>
        </aside>

        <div class="cot-noi-dung">
            <header class="thanh-tren">
                <span>
                    Trang quản trị /
                    <c:choose>
                        <c:when test="${muc eq 'set-do'}">Quản lý Set đồ</c:when>
                        <c:when test="${muc eq 'san-pham'}">Quản lý sản phẩm</c:when>
                        <c:when test="${muc eq 'danh-muc'}">Quản lý danh mục</c:when>
                        <c:when test="${muc eq 'thong-ke'}">Thống kê Affiliate</c:when>
                        <c:otherwise>Tổng quan</c:otherwise>
                    </c:choose>
                </span>
                <small>Administrator</small>
            </header>

            <main class="noi-dung-quan-tri" id="tong-quan">
<c:if test="${muc eq 'tong-quan'}">
    <section class="the-quan-ly tong-quan-so-lieu">
<h1>Tổng quan hệ thống</h1>
    <div class="cac-o-tong-quan">
        <div class="o-tong-quan">
            <span>Set đồ</span>
            <strong>${tongQuan.soSet}</strong>
        </div>

        <div class="o-tong-quan">
            <span>Sản phẩm</span>
            <strong>${tongQuan.soSanPham}</strong>
        </div>

        <div class="o-tong-quan">
            <span>Danh mục</span>
            <strong>${tongQuan.soDanhMuc}</strong>
        </div>

        <div class="o-tong-quan">
            <span>Lượt click</span>
            <strong>${tongQuan.soLuotClick}</strong>
        </div>
    </div>
</section>
</c:if>
<c:if test="${muc eq 'tong-quan' or muc eq 'thong-ke'}">
    <section class="khung-top-san-pham the-quan-ly">
        <h2>${muc eq 'thong-ke' ? 'Thống kê Affiliate' : 'Top sản phẩm có lượt click cao nhất'}</h2>
                        <c:if test="${muc eq 'thong-ke'}">
                            <p class="tong-click">Tổng lượt click <strong>${tongQuan.soLuotClick}</strong></p>
                        </c:if>

        <table class="bang-xep-hang">
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Tên sản phẩm</th>
                    <th>Lượt click</th>
                    <th>Xếp hạng</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sanPham" items="${topSanPham}" varStatus="dong">
                    <tr>
                        <td>${dong.count}</td>
                        <td><c:out value="${sanPham.name}" /></td>
                        <td>${sanPham.clickCount}</td>
                        <td>#${dong.count}</td>
                    </tr>
                </c:forEach>

                <c:if test="${empty topSanPham}">
                    <tr>
                        <td colspan="4">Chưa có sản phẩm để thống kê.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </section>
</c:if>
<c:if test="${muc eq 'set-do'}">
                    <div class="bo-cuc-set">
<section class="the-set">
<h2>Danh sách set đồ</h2>

                    <table class="bang-set-do">
                        <thead>
                            <tr>
                                <th>STT</th>
                                <th>Tên set đồ</th>
                                <th>Sản phẩm trong set</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="setDo" items="${danhSachSetDo}" varStatus="dong">
                                <tr>
                                    <td>${dong.count}</td>
                                    <td><c:out value="${setDo.title}" /></td>
                                    <td>
                                        <c:forEach var="mon" items="${setDo.products}">
                                            <span class="mon-trong-set"><c:out value="${mon.name}" /></span>
                                        </c:forEach>
                                        <c:if test="${empty setDo.products}">
                                            Chưa có sản phẩm trong set.
                                        </c:if>
                                    </td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/quan-tri" method="get">
                                            <input type="hidden" name="maSetSua" value="${setDo.outfitId}">
                                            <button type="submit">Sửa set</button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/quan-tri" method="post"
                                              onsubmit="return confirm('Bạn có chắc muốn xóa set đồ này?');">
                                            <input type="hidden" name="hanhDong" value="xoaSetDo">
                                            <input type="hidden" name="maSet" value="${setDo.outfitId}">
                                            <button type="submit">Xóa set</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty danhSachSetDo}">
                                <tr>
                                    <td colspan="4">Chưa có set đồ.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
</section>

                    <section class="the-set">
<c:choose>
<c:when test="${not empty setDangSua}">
                        <p>
                            <strong>Đang sửa set số ${setDangSua.outfitId}:</strong>
                            <c:out value="${setDangSua.title}" />
                        </p>
                        <form action="${pageContext.request.contextPath}/quan-tri" method="post">
                            <input type="hidden" name="hanhDong" value="suaSetDo">
                            <input type="hidden" name="maSet" value="${setDangSua.outfitId}">

                            <p>
                                <label for="tenSetSua">Tên set đồ</label>
                                <input type="text" id="tenSetSua" name="tenSetDo"
                                       value="<c:out value='${setDangSua.title}'/>"
                                       maxlength="200" required>
                            </p>

                            <fieldset>
                                <legend>Sản phẩm thuộc set đang sửa</legend>

                                <c:forEach var="mon" items="${danhSachSanPham}">
                                    <label>
                                        <input type="checkbox" name="maSanPhamTrongSet"
                                               value="${mon.productId}"
                                               ${maSanPhamDaChon.contains(mon.productId) ? 'checked' : ''}>
                                        <c:out value="${mon.name}" />
                                    </label>
                                    <br>
                                </c:forEach>
                            </fieldset>
                            <button type="submit">Lưu thay đổi set</button>
                            <a href="${duongDanQuanTri}?muc=set-do">Hủy chỉnh sửa</a>
                        </form>
                    </c:when>
                    <c:otherwise>
<h2>Thêm set đồ</h2>

                    <form action="${pageContext.request.contextPath}/quan-tri" method="post">
                        <input type="hidden" name="hanhDong" value="themSetDo">

                        <p>
                            <label for="tenSetDo">Tên set đồ</label>
                            <input type="text" id="tenSetDo" name="tenSetDo" required>
                        </p>

                        <fieldset>
                            <legend>Chọn sản phẩm thuộc set</legend>

                            <c:forEach var="sanPham" items="${danhSachSanPham}">
                                <label>
                                    <input type="checkbox" name="maSanPhamTrongSet"
                                           value="${sanPham.productId}">
                                    <c:out value="${sanPham.name}" />
                                </label>
                                <br>
                            </c:forEach>
                        </fieldset>
                            <button type="submit">Lưu set đồ</button>
                    </form>
</c:otherwise>
</c:choose>
</section>
</div>
                </c:if>
<c:if test="${muc eq 'san-pham'}">
    <div class="luoi-quan-ly">
        <section class="the-quan-ly">
            <h2>Danh sách sản phẩm</h2>

            <fmt:setLocale value="vi_VN" />

            <table class="bang-quan-ly">
                <thead>
                    <tr>
                        <th>Tên sản phẩm</th>
                        <th>Giá</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="sanPham" items="${danhSachSanPham}">
                        <tr>
                            <td><c:out value="${sanPham.name}" /></td>
                            <td><fmt:formatNumber value="${sanPham.price}" /> đ</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/quan-tri" method="get">
                                    <input type="hidden" name="maSanPhamSua" value="${sanPham.productId}">
                                    <button type="submit">Sửa</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/quan-tri"
                                      method="post"
                                      onsubmit="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">
                                    <input type="hidden" name="hanhDong" value="xoaSanPham">
                                    <input type="hidden" name="maSanPham" value="${sanPham.productId}">
                                    <button type="submit">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty danhSachSanPham}">
                        <tr>
                            <td colspan="3">Chưa có sản phẩm.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </section>
        <section class="the-quan-ly">
            <c:choose>
                <c:when test="${not empty sanPhamSua}">
                    <h2>Sửa sản phẩm</h2>
                    <form action="${pageContext.request.contextPath}/quan-tri" method="post">
                        <input type="hidden" name="hanhDong" value="suaSanPham">
                        <input type="hidden" name="maSanPham" value="${sanPhamSua.productId}">

                        <p>
                            <label for="tenSanPhamSua">Tên sản phẩm</label>
                            <input type="text" id="tenSanPhamSua" name="tenSanPham"
                                   value="<c:out value='${sanPhamSua.name}'/>"
                                   maxlength="200" required>
                        </p>

                        <p>
                            <label for="giaSanPhamSua">Giá bán (đồng)</label>
                            <fmt:formatNumber var="giaHienTai" value="${sanPhamSua.price}"
                                              groupingUsed="false" maxFractionDigits="0" />
                            <input type="number" id="giaSanPhamSua" name="giaSanPham"
                                   value="${giaHienTai}" min="0" step="1" required>
                        </p>
                        <button type="submit">Lưu thay đổi</button>
                        <a class="huy-sua" href="${duongDanQuanTri}?muc=san-pham">Hủy chỉnh sửa</a>
                    </form>
                </c:when>
                <c:otherwise>
                    <h2>Thêm sản phẩm</h2>

                    <form action="${pageContext.request.contextPath}/quan-tri" method="post">
                        <input type="hidden" name="hanhDong" value="themSanPham">

                        <p>
                            <label for="tenSanPham">Tên sản phẩm</label>
                            <input type="text" id="tenSanPham" name="tenSanPham" required>
                        </p>

                        <p>
                            <label for="giaSanPham">Giá bán (đồng)</label>
                            <input type="number" id="giaSanPham" name="giaSanPham"
                                   min="0" step="1" required>
                        </p>
                    <p>
                        <label for="danhMucSanPhamId">Danh mục sản phẩm</label>
                        <select id="danhMucSanPhamId" name="danhMucSanPhamId" required>
                            <option value="">-- Chọn danh mục con --</option>

                            <c:forEach var="cha" items="${danhMucCha}">
                                <c:forEach var="con" items="${danhMucConTheoCha[cha.categoryId]}">
                                    <option value="${con.categoryId}">
                                        <c:out value="${cha.name}" /> → <c:out value="${con.name}" />
                                    </option>
                                </c:forEach>
                            </c:forEach>
                        </select>
                    </p>
                    <p>
                        <label for="linkAnh">Link ảnh sản phẩm</label>
                        <input type="url" id="linkAnh" name="linkAnh"
                               placeholder="https://...">
                    </p>

                    <p>
                        <label for="linkMuaHang">Link mua hàng (Affiliate)</label>
                        <input type="url" id="linkMuaHang" name="linkMuaHang"
                               placeholder="https://..." required>
                    </p>

                    <p>
                        <label for="tenSan">Tên sàn</label>
                        <input type="text" id="tenSan" name="tenSan"
                               placeholder="Ví dụ: Shopee">
                    </p>
                    <button type="submit">Lưu sản phẩm</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </section>
    </div>
</c:if>
<c:if test="${muc eq 'danh-muc'}">
    <div class="luoi-quan-ly">
        <section class="the-quan-ly">
            <h2>Danh sách danh mục</h2>
            <table class="bang-quan-ly">
                <thead><tr><th>Danh mục cha</th><th>Danh mục con</th></tr></thead>
                <tbody>
                    <c:forEach var="cha" items="${danhMucCha}">
                        <tr>
                            <td><c:out value="${cha.name}" /></td>
                            <td>
                                <c:forEach var="con" items="${danhMucConTheoCha[cha.categoryId]}">
                                    <span class="ten-muc-con"><c:out value="${con.name}" /></span>
                                </c:forEach>
                                <c:if test="${empty danhMucConTheoCha[cha.categoryId]}">Chưa có danh mục con</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty danhMucCha}">
                        <tr><td colspan="2">Chưa có danh mục cha.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </section>
        <section class="the-quan-ly">
            <h2>Thêm danh mục con</h2>

            <form action="${pageContext.request.contextPath}/quan-tri" method="post">
                <input type="hidden" name="hanhDong" value="themDanhMucCon">

                <label for="danhMucChaId">Chọn danh mục cha</label>
                <select id="danhMucChaId" name="danhMucChaId" required>
                    <option value="">-- Chọn danh mục cha --</option>
                    <c:forEach var="cha" items="${danhMucCha}">
                        <option value="${cha.categoryId}">
                            <c:out value="${cha.name}" />
                        </option>
                    </c:forEach>
                </select>

                <p>
                    <label for="tenDanhMucCon">Tên danh mục con</label>
                    <input type="text" id="tenDanhMucCon" name="tenDanhMucCon" required>
                </p>

                <button type="submit">Lưu danh mục</button>
            </form>
        </section>
    </div>
</c:if>
</main>
    </div>
</div>
</body>
</html>
