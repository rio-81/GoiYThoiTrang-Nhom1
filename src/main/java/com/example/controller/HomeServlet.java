package com.example.controller;

import com.example.dao.OutfitDAO;
import com.example.model.Outfit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home", ""})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String bodyTypeParam = request.getParameter("bodyTypeId");
        String styleParam = request.getParameter("styleId");
        String seasonParam = request.getParameter("season");
        String occasionParam = request.getParameter("occasion");
        String categoryParam = request.getParameter("categoryId");
        String subCategoryParam = request.getParameter("subCategoryId");

        int bodyTypeId = (bodyTypeParam != null && !bodyTypeParam.isEmpty()) ? Integer.parseInt(bodyTypeParam) : 0;
        int styleId = (styleParam != null && !styleParam.isEmpty()) ? Integer.parseInt(styleParam) : 0;
        int categoryId = (categoryParam != null && !categoryParam.isEmpty()) ? Integer.parseInt(categoryParam) : 0;
        int subCategoryId = (subCategoryParam != null && !subCategoryParam.isEmpty()) ? Integer.parseInt(subCategoryParam) : 0;
        String season = (seasonParam != null && !seasonParam.isEmpty()) ? seasonParam : "ALL";
        String occasion = (occasionParam != null && !occasionParam.isEmpty()) ? occasionParam : "ALL";

        OutfitDAO dao = new OutfitDAO();
        List<Outfit> outfits = dao.filterOutfits(bodyTypeId, styleId, season, occasion, categoryId, subCategoryId);

        request.setAttribute("outfits", outfits);
        request.setAttribute("bodyTypes", dao.getAllBodyTypes());
        request.setAttribute("styles", dao.getAllStyles());
        request.setAttribute("occasions", dao.getAllOccasions());
        request.setAttribute("parentCategories", dao.getParentCategories());
        request.setAttribute("subCategories", dao.getSubCategoriesByParent(categoryId));

        request.setAttribute("selectedBodyType", bodyTypeId);
        request.setAttribute("selectedStyle", styleId);
        request.setAttribute("selectedSeason", season);
        request.setAttribute("selectedOccasion", occasion);
        request.setAttribute("selectedCategory", categoryId);
        request.setAttribute("selectedSubCategory", subCategoryId);

        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}