<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Edit Loyalty Reward Rate">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>
                <c:if test="${actionBean.loyaltyRate.canBeEdited}">
                    <security:protected-element name="manage-loyalty-rates">
                       <s:form action="/web/edit-loyalty-rate" name="createForm" id="createForm" method="post">
                        <input type="hidden" name="_eventName" id="_eventName" value="save"/>
                        <input type="hidden" name="loyaltyRate" id="loyaltyRate" value="${actionBean.loyaltyRate.id}"/>
                        <table class="alternating">
                                <tbody>
                                <tr>
                                    <td>
                                        Name: ${actionBean.loyaltyRate.name}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="unitlabel"/>
                                        <d:text style="background-color:#F0E68C" name="unit" value="${actionBean.unit}"/>
                                    </td>
                                </tr>
                                <tr valign="top">
                                    <td nowrap="nowrap">
                                        <fmt:message key="currencylabel"/>
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="currency" id="currency" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectcurrencylabel"/></s:option>
                                                   <c:forEach items="${actionBean.currencies}" var="currency"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.currency !=null &&
                                                             actionBean.currency == currency}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${currency}" ${selectedValue}/>
                                                      ${currency}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                     </td>
                                 </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="thresholdlabel"/>
                                      <d:text style="background-color:#F0E68C" name="threshold" value="${actionBean.threshold}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="valueperunitlabel"/>
                                      <d:text style="background-color:#F0E68C" name="valuePerUnit" value="${actionBean.valuePerUnit}"/>
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
                </c:if>
                    <security:when-no-content-displayed>
                        <d:link href="/web/user/site/help"><fmt:message
                                key="securityexceptionlink"/></d:link>
                    </security:when-no-content-displayed>

                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
