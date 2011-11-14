<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="menus" class="com.sonaattisetit.utils.MenusBean" scope="page"/>

<json:array name="menus" var="menu" items="${menus.menus}">
	<json:object>
		<json:property name="restaurant">${menu.restaurant}</json:property>
		<json:array name="dishes" var="dish" items="${menu.dishes}">
			<json:property name="dish">${dish}</json:property>
		</json:array>
	</json:object>	
</json:array>