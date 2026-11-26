package com.example.controller;

import com.example.dao.DuLieuQuanTri;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/mua-san-pham")
public class ChuyenDenSanPham extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int maSanPham;
        try {
            maSanPham = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(400, "Mã sản phẩm không hợp lệ.");
            return;
        }

        String linkMua;
        try {
            DuLieuQuanTri duLieu = new DuLieuQuanTri();
            linkMua = duLieu.ghiNhanLuotBam(maSanPham);
        } catch (Exception e) {
            throw new ServletException("Không thể ghi nhận lượt bấm.", e);
        }

        if (linkMua == null) {
            response.sendError(404, "Không tìm thấy link mua hàng của sản phẩm.");
            return;
        }

        response.sendRedirect(linkMua);
    }
}