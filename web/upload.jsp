
<%-- 
    Document   : upload
    Created on : Aug 26, 2025, 6:33:46 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload ảnh</title>
</head>
<body>
    <form action="upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" accept="image/*" required />
        <button type="submit">Upload</button>
    </form>
</body>
</html>
