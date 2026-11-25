package com.example.controller;

import com.example.dao.DuLieuQuanTri;
import com.example.dao.OutfitDAO;
import com.example.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/quan-tri")
public class DieuKhienQuanTri extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String hanhDong = request.getParameter("hanhDong");
        if ("xoaSetDo".equals(hanhDong)) {
            try {
                int maSet = Integer.parseInt(request.getParameter("maSet"));
                DuLieuQuanTri duLieu = new DuLieuQuanTri();

                if (!duLieu.xoaSetDo(maSet)) {
                    response.sendError(404, "Không tìm thấy set đồ cần xóa.");
                    return;
                }
            } catch (NumberFormatException e) {
                response.sendError(400, "Mã set đồ không hợp lệ.");
                return;
            } catch (java.sql.SQLException e) {
                if (e.getErrorCode() == 547) {
                    response.sendError(409, "Set đồ đang có dữ liệu liên quan nên chưa thể xóa.");
                    return;
                }
                throw new ServletException("Không thể xóa set đồ.", e);
            } catch (Exception e) {
                throw new ServletException("Không thể xóa set đồ.", e);
            }

            response.sendRedirect(request.getContextPath() + "/quan-tri?muc=set-do");
            return;
        }
        if ("suaSetDo".equals(hanhDong)) {
            String tenSet = request.getParameter("tenSetDo");
            String[] cacMaDaChon = request.getParameterValues("maSanPhamTrongSet");

            if (cacMaDaChon == null || cacMaDaChon.length == 0) {
                response.sendError(400, "Hãy chọn ít nhất một sản phẩm cho set.");
                return;
            }

            try {
                int maSet = Integer.parseInt(request.getParameter("maSet"));
                int[] maSanPham = new int[cacMaDaChon.length];

                for (int i = 0; i < cacMaDaChon.length; i++) {
                    maSanPham[i] = Integer.parseInt(cacMaDaChon[i]);
                }

                DuLieuQuanTri duLieu = new DuLieuQuanTri();
                duLieu.suaSetDo(maSet, tenSet, maSanPham);
            } catch (NumberFormatException e) {
                response.sendError(400, "Mã set hoặc mã sản phẩm không hợp lệ.");
                return;
            } catch (IllegalArgumentException e) {
                response.sendError(400, e.getMessage());
                return;
            } catch (Exception e) {
                throw new ServletException("Không thể lưu thay đổi set đồ.", e);
            }

            response.sendRedirect(request.getContextPath() + "/quan-tri?muc=set-do");
            return;
        }
        if ("themSetDo".equals(hanhDong)) {
            String tenSetDo = request.getParameter("tenSetDo");
            String[] cacMaDaChon = request.getParameterValues("maSanPhamTrongSet");

            if (cacMaDaChon == null || cacMaDaChon.length == 0) {
                response.sendError(400, "Hãy chọn ít nhất một sản phẩm cho set.");
                return;
            }

            try {
                int[] maSanPham = new int[cacMaDaChon.length];

                for (int i = 0; i < cacMaDaChon.length; i++) {
                    maSanPham[i] = Integer.parseInt(cacMaDaChon[i]);
                }

                DuLieuQuanTri duLieu = new DuLieuQuanTri();
                duLieu.themSetDo(tenSetDo, maSanPham);
            } catch (NumberFormatException e) {
                response.sendError(400, "Mã sản phẩm không hợp lệ.");
                return;
            } catch (IllegalArgumentException e) {
                response.sendError(400, e.getMessage());
                return;
            } catch (Exception e) {
                throw new ServletException("Không thể lưu set đồ.", e);
            }

            response.sendRedirect(request.getContextPath() + "/quan-tri?muc=set-do");
            return;
        }
        if ("xoaSanPham".equals(hanhDong)) {
            try {
                int ma = Integer.parseInt(request.getParameter("maSanPham"));

                DuLieuQuanTri duLieu = new DuLieuQuanTri();
                if (!duLieu.xoaSanPham(ma)) {
                    response.sendError(404, "Không tìm thấy sản phẩm cần xóa.");
                    return;
                }
            } catch (NumberFormatException e) {
                response.sendError(400, "Mã sản phẩm không hợp lệ.");
                return;
            } catch (java.sql.SQLException e) {
                if (e.getErrorCode() == 547) {
                    response.sendError(409, "Sản phẩm đang có dữ liệu liên quan nên chưa thể xóa.");
                    return;
                }
                throw new ServletException("Không thể xóa sản phẩm.", e);
            } catch (Exception e) {
                throw new ServletException("Không thể xóa sản phẩm.", e);
            }

            response.sendRedirect(request.getContextPath() + "/quan-tri?muc=san-pham");
            return;
        }
        if ("suaSanPham".equals(hanhDong)) {
            String ten = request.getParameter("tenSanPham");

            if (ten == null || ten.trim().isEmpty() || ten.trim().length() > 200) {
                response.sendError(400, "Tên sản phẩm phải có từ 1 đến 200 ký tự.");
                return;
            }

            try {
                int ma = Integer.parseInt(request.getParameter("maSanPham"));
                long gia = Long.parseLong(request.getParameter("giaSanPham"));

                if (gia < 0 || gia > 999_999_999_999_999_999L) {
                    response.sendError(400, "Giá bán phải từ 0 đến 18 chữ số.");
                    return;
                }

                DuLieuQuanTri duLieu = new DuLieuQuanTri();
                duLieu.suaSanPham(ma, ten, gia);

            } catch (NumberFormatException e) {
                response.sendError(400, "Mã sản phẩm hoặc giá bán không hợp lệ.");
                return;
            } catch (IllegalArgumentException e) {
                response.sendError(404, "Không tìm thấy sản phẩm cần sửa.");
                return;
            } catch (Exception e) {
                throw new ServletException("Không thể cập nhật sản phẩm.", e);
            }

            response.sendRedirect(request.getContextPath() + "/quan-tri?muc=san-pham");
            return;
        }
        if ("themSanPham".equals(hanhDong)) {
            String ten = request.getParameter("tenSanPham");
            String giaNhap = request.getParameter("giaSanPham");
            String maDanhMuc = request.getParameter("danhMucSanPhamId");
            String linkAnh = request.getParameter("linkAnh");
            String linkMua = request.getParameter("linkMuaHang");
            String tenSan = request.getParameter("tenSan");

            if (ten == null || ten.trim().isEmpty()
                    || linkMua == null || linkMua.trim().isEmpty()) {
                response.sendError(400, "Vui lòng nhập tên sản phẩm và link mua hàng.");
                return;
            }

            try {
                long gia = Long.parseLong(giaNhap);
                int danhMucId = Integer.parseInt(maDanhMuc);

                if (gia < 0 || gia > 999_999_999_999_999_999L) {
                    response.sendError(400, "Giá phải là số nguyên từ 0 đến 18 chữ số.");
                    return;
                }

                DuLieuQuanTri duLieu = new DuLieuQuanTri();
                duLieu.themSanPham(ten, gia, danhMucId, linkAnh, linkMua.trim(), tenSan);
            } catch (NumberFormatException e) {
                response.sendError(400, "Vui lòng nhập giá bằng số nguyên và chọn danh mục.");
                return;
            } catch (IllegalArgumentException e) {
                response.sendError(400, "Vui lòng chọn danh mục con hợp lệ.");
                return;
            } catch (Exception e) {
                throw new ServletException("Không thể lưu sản phẩm.", e);
            }

            response.sendRedirect(request.getContextPath() + "/quan-tri?muc=san-pham");
            return;
        }
        String maCha = request.getParameter("danhMucChaId");
        String tenCon = request.getParameter("tenDanhMucCon");

        if (!"themDanhMucCon".equals(hanhDong)) {
            response.sendError(400, "Thao tác không hợp lệ.");
            return;
        }

        if (tenCon == null || tenCon.trim().isEmpty()) {
            response.sendError(400, "Vui lòng nhập tên danh mục con.");
            return;
        }

        try {
            int danhMucChaId = Integer.parseInt(maCha);
            DuLieuQuanTri duLieu = new DuLieuQuanTri();
            duLieu.themDanhMucCon(danhMucChaId, tenCon);
        } catch (IllegalArgumentException e) {
            response.sendError(400, "Vui lòng chọn danh mục cha hợp lệ.");
            return;
        } catch (Exception e) {
            throw new ServletException("Không thể lưu danh mục con.", e);
        }

        response.sendRedirect(request.getContextPath() + "/quan-tri?muc=danh-muc");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DuLieuQuanTri duLieu = new DuLieuQuanTri();

        try {
            List<Product> danhSach = duLieu.layDanhSachSanPham();
            request.setAttribute("danhSachSanPham", danhSach);
            request.setAttribute("topSanPham", duLieu.layTopSanPhamTheoLuotBam());
            request.setAttribute("tongQuan", duLieu.laySoLieuTongQuan());
            String maCanSua = request.getParameter("maSanPhamSua");

            if (maCanSua != null) {
                for (Product sanPham : danhSach) {
                    if (String.valueOf(sanPham.getProductId()).equals(maCanSua)) {
                        request.setAttribute("sanPhamSua", sanPham);
                        break;
                    }
                }
            }
            OutfitDAO outfitDAO = new OutfitDAO();
            var danhSachSetDo = outfitDAO.filterOutfits(0, 0, "ALL", "ALL", 0, 0);
            danhSachSetDo.sort(java.util.Comparator.comparingInt(setDo -> setDo.getOutfitId()));
            request.setAttribute("danhSachSetDo", danhSachSetDo);

            String maSetSua = request.getParameter("maSetSua");
            if (maSetSua != null) {
                for (var setDo : danhSachSetDo) {
                    if (String.valueOf(setDo.getOutfitId()).equals(maSetSua)) {
                        request.setAttribute("setDangSua", setDo);
                        var maSanPhamDaChon = new java.util.HashSet<Integer>();

                        for (var mon : setDo.getProducts()) {
                            maSanPhamDaChon.add(mon.getProductId());
                        }

                        request.setAttribute("maSanPhamDaChon", maSanPhamDaChon);
                        break;
                    }
                }
            }
            var danhMucCha = outfitDAO.getParentCategories();
            var danhMucConTheoCha = new java.util.LinkedHashMap<Integer, java.util.List<com.example.model.Category>>();

            for (var cha : danhMucCha) {
                int maCha = cha.getCategoryId();
                danhMucConTheoCha.put(maCha, outfitDAO.getSubCategoriesByParent(maCha));
            }

            request.setAttribute("danhMucCha", danhMucCha);
            request.setAttribute("danhMucConTheoCha", danhMucConTheoCha);
        } catch (Exception e) {
            throw new ServletException("Không thể tải danh sách sản phẩm.", e);
        }

        request.getRequestDispatcher("/WEB-INF/views/trang-quan-tri.jsp")
                .forward(request, response);
    }
}
