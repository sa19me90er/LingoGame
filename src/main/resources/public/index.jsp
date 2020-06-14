<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Spring Boot Session Management Example</title>
</head>
<body>
<div>
    <form th:action="@{/addNote}" method="get">
        <textarea name="note" cols="40" rows="2"></textarea>
        <br> <input type="submit" value="Add Note" />
    </form>
</div>
<div>
    <form th:action="@{/invalidate/session}" method="get">
        <input type="submit" value="Destroy Session" />
    </form>
</div>
<div>
    <h2>Notes</h2>
    <ul th:each="note : ">
        <li th:text="">note</li>
    </ul>
</div>

</body>
</html>