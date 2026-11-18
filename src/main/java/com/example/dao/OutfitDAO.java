package com.example.dao;

import com.example.context.DBContext;
import com.example.model.BodyType;
import com.example.model.Category;
import com.example.model.Outfit;
import com.example.model.Product;
import com.example.model.Style;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OutfitDAO {

    public List<BodyType> getAllBodyTypes() {
        List<BodyType> list = new ArrayList<>();
        String sql = "SELECT * FROM BodyTypes";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new BodyType(
                        rs.getInt("body_type_id"),
                        rs.getNString("name"),
                        rs.getNString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Style> getAllStyles() {
        List<Style> list = new ArrayList<>();
        String sql = "SELECT * FROM Styles";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Style(
                        rs.getInt("style_id"),
                        rs.getNString("name"),
                        rs.getNString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Category> getParentCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Categories WHERE parent_id IS NULL";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("category_id"),
                        rs.getNString("name"),
                        null
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Category> getSubCategoriesByParent(int parentId) {
        List<Category> list = new ArrayList<>();
        if (parentId <= 0) return list;
        String sql = "SELECT * FROM Categories WHERE parent_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, parentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Category(
                            rs.getInt("category_id"),
                            rs.getNString("name"),
                            rs.getInt("parent_id")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getAllOccasions() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT occasion FROM Outfits WHERE occasion IS NOT NULL AND occasion <> ''";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getNString("occasion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getProductsByOutfitId(int outfitId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.* FROM Products p INNER JOIN OutfitItems oi ON p.product_id = oi.product_id WHERE oi.outfit_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, outfitId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("product_id"),
                            rs.getInt("category_id"),
                            rs.getNString("name"),
                            rs.getDouble("price"),
                            rs.getString("image_url"),
                            rs.getString("affiliate_url"),
                            rs.getNString("platform"),
                            rs.getInt("click_count")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Outfit> filterOutfits(int bodyTypeId, int styleId, String season, String occasion, int categoryId, int subCategoryId) {
        List<Outfit> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT o.*, s.name AS style_name, b.name AS body_type_name " +
                        "FROM Outfits o " +
                        "LEFT JOIN Styles s ON o.style_id = s.style_id " +
                        "LEFT JOIN BodyTypes b ON o.body_type_id = b.body_type_id " +
                        "LEFT JOIN OutfitItems oi ON o.outfit_id = oi.outfit_id " +
                        "LEFT JOIN Products p ON oi.product_id = p.product_id " +
                        "LEFT JOIN Categories c ON p.category_id = c.category_id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();
        if (bodyTypeId > 0) {
            sql.append("AND o.body_type_id = ? ");
            params.add(bodyTypeId);
        }
        if (styleId > 0) {
            sql.append("AND o.style_id = ? ");
            params.add(styleId);
        }
        if (season != null && !season.trim().isEmpty() && !season.equals("ALL")) {
            sql.append("AND o.season = ? ");
            params.add(season);
        }
        if (occasion != null && !occasion.trim().isEmpty() && !occasion.equals("ALL")) {
            sql.append("AND o.occasion = ? ");
            params.add(occasion);
        }
        if (subCategoryId > 0) {
            sql.append("AND p.category_id = ? ");
            params.add(subCategoryId);
        } else if (categoryId > 0) {
            sql.append("AND (p.category_id = ? OR c.parent_id = ?) ");
            params.add(categoryId);
            params.add(categoryId);
        }
        sql.append("ORDER BY o.created_at DESC");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Outfit o = new Outfit();
                    o.setOutfitId(rs.getInt("outfit_id"));
                    o.setTitle(rs.getNString("title"));
                    o.setDescription(rs.getNString("description"));
                    o.setImageUrl(rs.getString("image_url"));
                    o.setStyleId(rs.getInt("style_id"));
                    o.setBodyTypeId(rs.getInt("body_type_id"));
                    o.setGender(rs.getNString("gender"));
                    o.setSeason(rs.getNString("season"));
                    o.setOccasion(rs.getNString("occasion"));
                    o.setStyleName(rs.getNString("style_name"));
                    o.setBodyTypeName(rs.getNString("body_type_name"));
                    o.setProducts(getProductsByOutfitId(o.getOutfitId()));
                    list.add(o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}