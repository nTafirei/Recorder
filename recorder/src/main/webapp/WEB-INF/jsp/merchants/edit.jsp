<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Edit Merchant">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>

                    <security:protected-element name="manage-merchants">
                       <s:form action="/web/edit-merchant" name="createForm" id="createForm" method="post">
                        <input type="hidden" name="_eventName" id="_eventName" value="save"/>
                        <input type="hidden" name="merchant" id="merchant" value="${actionBean.merchant.id}"/>
                        <table width="100%">
                            <tr>
                                <td align="center" colspan="3">
                                    <fmt:message key="editmerchantdetailslabel"/>: ${actionBean.merchant.name}
                                </td>
                            </tr>
                        </table>
                        <table class="alternating">
                                <tbody>
                                <tr>
                                    <td>
                                        <fmt:message key="namelabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="name" value="${actionBean.name}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="addresslabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="address" value="${actionBean.address}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="citylabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="city" value="${actionBean.city}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="emaillabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="email" value="${actionBean.email}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="phonenumberslabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="phoneNumbers" value="${actionBean.phoneNumbers}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="contactpersonlabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="contactPerson" value="${actionBean.contactPerson}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="taxIdlabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="taxId" value="${actionBean.taxId}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="urllabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="url" value="${actionBean.url}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="apikeylabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="apiKey" value="${actionBean.apiKey}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="statuslabel"/>
                                    </td>
                                    <td nowrap="nowrap">
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="activeStatus" id="activeStatus" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectstatuslabel"/></s:option>
                                                   <c:forEach items="${actionBean.activeStatuses}" var="activeStatus"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.activeStatus !=null &&
                                                             actionBean.activeStatus == activeStatus}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${activeStatus}" ${selectedValue}/>
                                                      ${activeStatus.status}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
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
