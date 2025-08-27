/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author DELL
 */
public class InputSanitizer {
    public static String cleanSearchQuery(String query) {
        if (query == null) return null;

        // Trim, loại bỏ khoảng trắng dư thừa
        query = query.trim();

        // Thay nhiều dấu cách liên tiếp bằng một dấu cách
        query = query.replaceAll("\\s{2,}", " ");

        return query;
    }
}
