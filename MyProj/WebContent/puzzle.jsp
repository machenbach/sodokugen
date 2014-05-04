<%@page import="java.util.Random"%>
<%@page import="org.mike.util.Range"%>
<%@page import="org.mike.puzzle.Puzzle"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table, tr, td
{
padding:0;
border-collapse:collapse;
text-align:center;
border: 1px solid black;
width:45px;
height:45px;
}
table {
width:100%;
height:100%;
font-family:sans-serif;
font-size:20px;
font-weight:bold;
}
</style>
</head>
<body>
<% Puzzle p = new Puzzle(); 
   Random rnd = new Random(); %>
<table style="width:360px; height:360px; align:center; border:2px solid black; margin-left:auto; margin-right:auto">
<% for (int tr : new Range(3)) {%>
<tr>
	<% for (int tc : new Range(3)) { %>
	<td>
	<table>
		<% for (int r : new Range(3)) { %>
			<tr>
			<% for (int c : new Range(3)) { %>
				<td><%= p.puzzleElement(tr * 3 + r, tc *3 + c)  %></td>
			<% } %>
			</tr>
		<% } %>
	</table>
	</td>
	<% } %>
</tr>
<%} %>
</table>
</body>
