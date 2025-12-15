<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Edit Charge Rate">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>

                    <security:protected-element name="manage-charge-rates">
                       <fmt:message key="addchargeratelabel"/> in ${actionBean.currency}
                       <s:form action="/web/new-charge-rate" name="createForm" id="createForm" method="post">
                        <input type="hidden" name="_eventName" id="_eventName" value="save"/>
                        <table class="alternating">
                                <tbody>
                                <tr>
                                    <td colspan="2">
                                      <fmt:message key="namelabel"/>
                                      <d:text style="background-color:#F0E68C" name="name" value="${actionBean.name}"/>
                                    </td>
                                    <td colspan="2">
                                        <fmt:message key="amountlabel"/>
                                        <d:text style="background-color:#F0E68C" name="amount" value="${actionBean.amount}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="transactiontypelabel"/> :
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="transactionType" id="transactionType" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selecttransactiontypelabel"/></s:option>
                                                   <c:forEach items="${actionBean.transactionTypes}" var="transactionType"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.transactionType !=null &&
                                                             actionBean.transactionType == transactionType}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${transactionType}" ${selectedValue}/>
                                                      ${transactionType}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                    </td>
                                    <td>
                                        <fmt:message key="chargetypelabel"/> :
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="chargeType" id="chargeType" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectchargetypelabel"/></s:option>
                                                   <c:forEach items="${actionBean.chargeTypes}" var="chargeType"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.chargeType !=null &&
                                                             actionBean.chargeType == chargeType}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${chargeType}" ${selectedValue}/>
                                                      ${chargeType}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="rangestartlabel"/>
                                        <d:text style="background-color:#F0E68C" name="rangeStart" value="${actionBean.rangeStart}"/>
                                    </td>
                                    <td>
                                        <fmt:message key="rangeendlabel"/>
                                        <d:text style="background-color:#F0E68C" name="rangeEnd" value="${actionBean.rangeEnd}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right">
                                        <fmt:message key="submitlabel" var="submitlabel"/>
                                        <d:submit class="small" name="save" value="${submitlabel}"/>
                                    </td>
                                </tr>
                            <tbody>
                        </table>
                      </s:form>
                    </security:protected-element>
                    <security:when-no-content-displayed>
                        <d:link href="/web/user/site/help"><fmt:message
                                key="securityexceptionlink"/></d:link>
                    </security:when-no-content-displayed>

                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
