<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${pageComputer.elements}" var="computer">
	<tr>
		<td class="editMode">
			<input type="checkbox" id="computerId" name="cb" class="cb" value="${computer.id}">
		</td>
		<td>
			<a id="editLink" href="editComputer?id=${computer.id}" onclick="">
            	${computer.name }
           	</a>
        </td>
        <td>
        	<c:if test="${computer.introduced != null }">
        		${computer.introduced}
        	</c:if>
        </td>
        <td>
        	<c:if test="${computer.discontinued != null }">
        		${computer.discontinued}
        	</c:if>
        </td>
        <td>
        	<c:if test="${computer.companyName != null }">
        		${computer.companyName}
        	</c:if>
        </td>
	</tr>
</c:forEach>