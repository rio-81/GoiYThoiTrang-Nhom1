package com.example.dao;

import com.example.context.DBContext;
import com.example.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DuLieuQuanTri {
    public java.util.Map<String, Long> laySoLieuTongQuan() throws Exception {
        String sql = "SELECT "
                + "(SELECT COUNT_BIG(*) FROM dbo.Outfits) AS soSet, "
                + "(SELECT COUNT_BIG(*) FROM dbo.Products) AS soSanPham, "
                + "(SELECT COUNT_BIG(*) FROM dbo.Categories) AS soDanhMuc, "
                + "(SELECT COALESCE(SUM(CAST(click_count AS BIGINT)), 0) "
                + " FROM dbo.Products) AS soLuotClick";

        try (Connection ketNoi = DBContext.getConnection();
             PreparedStatement lenh = ketNoi.prepareStatement(sql);
             ResultSet ketQua = lenh.executeQuery()) {

            ketQua.next();
            return java.util.Map.of(
                    "soSet", ketQua.getLong("soSet"),
                    "soSanPham", ketQua.getLong("soSanPham"),
                    "soDanhMuc", ketQua.getLong("soDanhMuc"),
                    "soLuotClick", ketQua.getLong("soLuotClick")
            );
        }
    }
    public String ghiNhanLuotBam(int maSanPham) throws Exception {
        String sql = "UPDATE dbo.Products "
                + "SET click_count = COALESCE(click_count, 0) + 1 "
                + "OUTPUT INSERTED.affiliate_url "
                + "WHERE product_id = ? "
                + "AND (affiliate_url LIKE 'https://%' OR affiliate_url LIKE 'http://%')";

        try (Connection ketNoi = DBContext.getConnection();
             PreparedStatement lenh = ketNoi.prepareStatement(sql)) {

            lenh.setInt(1, maSanPham);

            try (ResultSet ketQua = lenh.executeQuery()) {
                if (ketQua.next()) {
                    return ketQua.getString("affiliate_url");
                }
                return null;
            }
        }
    }
    public List<Product> layTopSanPhamTheoLuotBam() throws Exception {
        List<Product> danhSach = new ArrayList<>();

        String sql = "SELECT TOP (10) product_id, category_id, name, price, "
                + "image_url, affiliate_url, platform, "
                + "COALESCE(click_count, 0) AS luot_bam "
                + "FROM dbo.Products "
                + "ORDER BY luot_bam DESC, product_id ASC";

        try (Connection ketNoi = DBContext.getConnection();
             PreparedStatement lenh = ketNoi.prepareStatement(sql);
             ResultSet ketQua = lenh.executeQuery()) {

            while (ketQua.next()) {
                Product sanPham = new Product(
                        ketQua.getInt("product_id"),
                        ketQua.getInt("category_id"),
                        ketQua.getNString("name"),
                        ketQua.getDouble("price"),
                        ketQua.getString("image_url"),
                        ketQua.getString("affiliate_url"),
                        ketQua.getNString("platform"),
                        ketQua.getInt("luot_bam")
                );
                danhSach.add(sanPham);
            }
        }

        return danhSach;
    }
    public boolean xoaSetDo(int maSet) throws Exception {
        String sqlGoSanPham = "DELETE FROM OutfitItems WHERE outfit_id = ?";
        String sqlXoaSet = "DELETE FROM Outfits WHERE outfit_id = ?";

        try (Connection ketNoi = DBContext.getConnection()) {
            ketNoi.setAutoCommit(false);

            try (PreparedStatement goSanPham = ketNoi.prepareStatement(sqlGoSanPham);
                 PreparedStatement xoaSet = ketNoi.prepareStatement(sqlXoaSet)) {

                goSanPham.setInt(1, maSet);
                goSanPham.executeUpdate();

                xoaSet.setInt(1, maSet);
                boolean daXoa = xoaSet.executeUpdate() == 1;

                ketNoi.commit();
                return daXoa;
            } catch (Exception e) {
                ketNoi.rollback();
                throw e;
            }
        }
    }
    public void suaSetDo(int maSet, String tenSetDo, int[] maSanPham) throws Exception {
        if (tenSetDo == null || tenSetDo.trim().isEmpty()
                || tenSetDo.trim().length() > 200) {
            throw new IllegalArgumentException("Tên set đồ phải có từ 1 đến 200 ký tự.");
        }

        if (maSanPham == null || maSanPham.length == 0) {
            throw new IllegalArgumentException("Hãy chọn ít nhất một sản phẩm cho set.");
        }

        String sqlDoiTen = "UPDATE Outfits SET title = ? WHERE outfit_id = ?";
        String sqlBoMonCu = "DELETE FROM OutfitItems WHERE outfit_id = ?";
        String sqlGanMon = "INSERT INTO OutfitItems (outfit_id, product_id) VALUES (?, ?)";

        try (Connection ketNoi = DBContext.getConnection()) {
            ketNoi.setAutoCommit(false);

            try (PreparedStatement doiTen = ketNoi.prepareStatement(sqlDoiTen);
                 PreparedStatement boMonCu = ketNoi.prepareStatement(sqlBoMonCu);
                 PreparedStatement ganMon = ketNoi.prepareStatement(sqlGanMon)) {

                doiTen.setNString(1, tenSetDo.trim());
                doiTen.setInt(2, maSet);
                if (doiTen.executeUpdate() == 0) {
                    throw new IllegalArgumentException("Không tìm thấy set đồ cần sửa.");
                }

                boMonCu.setInt(1, maSet);
                boMonCu.executeUpdate();

                for (int ma : maSanPham) {
                    ganMon.setInt(1, maSet);
                    ganMon.setInt(2, ma);
                    ganMon.addBatch();
                }
                ganMon.executeBatch();

                ketNoi.commit();
            } catch (Exception e) {
                ketNoi.rollback();
                throw e;
            }
        }
    }
    public void themSetDo(String tenSetDo, int[] maSanPham) throws Exception {
        if (tenSetDo == null || tenSetDo.trim().isEmpty()
                || tenSetDo.trim().length() > 200) {
            throw new IllegalArgumentException("Tên set đồ phải có từ 1 đến 200 ký tự.");
        }

        if (maSanPham == null || maSanPham.length == 0) {
            throw new IllegalArgumentException("Hãy chọn ít nhất một sản phẩm.");
        }

        String sqlThemSet = "INSERT INTO Outfits (title) "
                + "OUTPUT INSERTED.outfit_id VALUES (?)";
        String sqlThemMon = "INSERT INTO OutfitItems (outfit_id, product_id) VALUES (?, ?)";

        try (Connection ketNoi = DBContext.getConnection()) {
            ketNoi.setAutoCommit(false);

            try (PreparedStatement lenhSet = ketNoi.prepareStatement(sqlThemSet);
                 PreparedStatement lenhMon = ketNoi.prepareStatement(sqlThemMon)) {

                lenhSet.setNString(1, tenSetDo.trim());

                int maSetMoi;
                try (ResultSet ketQua = lenhSet.executeQuery()) {
                    if (!ketQua.next()) {
                        throw new java.sql.SQLException("Không lấy được mã set vừa tạo.");
                    }
                    maSetMoi = ketQua.getInt(1);
                }

                for (int ma : maSanPham) {
                    lenhMon.setInt(1, maSetMoi);
                    lenhMon.setInt(2, ma);
                    lenhMon.addBatch();
                }

                lenhMon.executeBatch();
                ketNoi.commit();
            } catch (Exception e) {
                ketNoi.rollback();
                throw e;
            }
        }
    }
    public boolean xoaSanPham(int maSanPham) throws Exception {
        String sql = "DELETE FROM Products WHERE product_id = ?";

        try (Connection ketNoi = DBContext.getConnection();
             PreparedStatement lenh = ketNoi.prepareStatement(sql)) {

            lenh.setInt(1, maSanPham);
            return lenh.executeUpdate() == 1;
        }
    }
    public void suaSanPham(int maSanPham, String tenSanPham, long giaSanPham)
            throws Exception {

        String sql = "UPDATE Products SET name = ?, price = ? WHERE product_id = ?";

        try (Connection ketNoi = DBContext.getConnection();
             PreparedStatement lenh = ketNoi.prepareStatement(sql)) {

            lenh.setNString(1, tenSanPham.trim());
            lenh.setLong(2, giaSanPham);
            lenh.setInt(3, maSanPham);

            if (lenh.executeUpdate() == 0) {
                throw new IllegalArgumentException("Không tìm thấy sản phẩm cần sửa.");
            }
        }
    }
    public void themSanPham(String tenSanPham, long giaSanPham, int danhMucSanPhamId,
                            String linkAnh, String linkMuaHang, String tenSan)
            throws Exception {

        String sql = "INSERT INTO Products "
                + "(name, price, category_id, image_url, affiliate_url, platform) "
                + "SELECT ?, ?, category_id, ?, ?, ? FROM Categories "
                + "WHERE category_id = ? AND parent_id IS NOT NULL";

        try (Connection ketNoi = DBContext.getConnection();
             PreparedStatement lenh = ketNoi.prepareStatement(sql)) {

            lenh.setNString(1, tenSanPham.trim());
            lenh.setLong(2, giaSanPham);
            lenh.setString(3, linkAnh);
            lenh.setString(4, linkMuaHang);
            lenh.setNString(5, tenSan);
            lenh.setInt(6, danhMucSanPhamId);

            if (lenh.executeUpdate() == 0) {
                throw new IllegalArgumentException("Vui lòng chọn danh mục con hợp lệ.");
            }
        }
    }

    public List<Product> layDanhSachSanPham() throws Exception {
        List<Product> danhSach = new ArrayList<>();

        String sql = "SELECT product_id, category_id, name, price, "
                + "image_url, affiliate_url, platform, click_count "
                + "FROM Products ORDER BY product_id";

        try (Connection ketNoi = DBContext.getConnection();
             PreparedStatement lenh = ketNoi.prepareStatement(sql);
             ResultSet ketQua = lenh.executeQuery()) {

            while (ketQua.next()) {
                Product sanPham = new Product(
                        ketQua.getInt("product_id"),
                        ketQua.getInt("category_id"),
                        ketQua.getNString("name"),
                        ketQua.getDouble("price"),
                        ketQua.getString("image_url"),
                        ketQua.getString("affiliate_url"),
                        ketQua.getNString("platform"),
                        ketQua.getInt("click_count")
                );

                danhSach.add(sanPham);
            }
        }

        return danhSach;
    }

public void themDanhMucCon(int danhMucChaId, String tenDanhMucCon)
        throws Exception {

    String sql = "INSERT INTO Categories (name, parent_id) "
            + "SELECT ?, category_id FROM Categories "
            + "WHERE category_id = ? AND parent_id IS NULL";

    try (Connection ketNoi = DBContext.getConnection();
         PreparedStatement lenh = ketNoi.prepareStatement(sql)) {

        lenh.setNString(1, tenDanhMucCon.trim());
        lenh.setInt(2, danhMucChaId);

        if (lenh.executeUpdate() == 0) {
            throw new IllegalArgumentException("Danh mục cha không tồn tại.");
        }
    }
}
}
