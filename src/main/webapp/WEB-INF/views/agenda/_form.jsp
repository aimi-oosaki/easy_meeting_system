<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actAge" value="${ForwardConst.ACT_AGE.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<script>
    var list_number = parseInt(${AttributeConst.AGENDA_LIST_SIZE.getValue()});
</script>

<%-- フォーム --%>
<br /><br />
<table class="">
    <thead>
        <tr>
           <th>議題</th>
        </tr>
    </thead>
    <tbody>

        <c:forEach var="agenda" items="${agendas}">
            <tr>
            <td>
               <input type="text" name="${AttributeConst.AGE_TITLE.getValue()}[]" value="${agenda.title}" />
               <input type="hidden" name="${AttributeConst.AGE_ID.getValue()}[]" value="${agenda.id}" />
            </td>
            </tr>
        </c:forEach>
    </tbody>
    <tfoot>
        <tr>
            <td ><button id="add" type="button" class="btn-rounded">＋</button></td>
        </tr>
    </tfoot>

</table>
<br /><br />
<input type="hidden" name="${AttributeConst.MET_ID.getValue()}" value="${meeting.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>