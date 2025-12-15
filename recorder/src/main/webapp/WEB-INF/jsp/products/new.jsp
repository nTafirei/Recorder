<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Add New Product">

    <s:layout-component name="head">
        <link rel="stylesheet" href="${actionBean.cssPath}/jquery-ui.css"/>
        <script src="${actionBean.scriptPath}/jquery-3.6.0.min.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui-timepicker-addon.min.js"></script>
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>
                    <security:protected-element name="manage-products">
                       <s:form action="/web/new-product" name="createForm" id="createForm" method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="save"/>
                         <input type="hidden" name="merchant" id="merchant" value="${actionBean.merchant.id}"/>

                        <table width="100%">
                            <tr>
                                <td align="center" colspan="3">
                                    <fmt:message key="addnewproductlabel"/> for ${actionBean.merchant.name}
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
                                        <d:text style="background-color:#F0E68C" name="name"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="availabilitydatelabel"/> (dd-mm-yyyy)
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="availabilityDate" id="availabilityDate" class="availabilityDate"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="expirydatelabel"/> (dd-mm-yyyy)
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="expiryDate"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="minpurchaselabel"/> (in ${actionBean.purchaseCurrency})
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="minPurchase"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="maxpurchaselabel"/> (in ${actionBean.purchaseCurrency})
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="maxPurchase"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="merchantlabel"/>
                                    </td>
                                    <td nowrap="nowrap">
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="merchant" id="merchant" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectmerchantlabel"/></s:option>
                                                   <c:forEach items="${actionBean.merchants}" var="merchant"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.merchant !=null &&
                                                             actionBean.merchant.id == merchant.id}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${merchant.id}" ${selectedValue}/>
                                                      ${merchant.name}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        <fmt:message key="producttypelabel"/>
                                    </td>
                                    <td nowrap="nowrap">
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="productType" id="productType" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectproducttypelabel"/></s:option>
                                                   <c:forEach items="${actionBean.productTypes}" var="productType"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.productType !=null &&
                                                             actionBean.productType == productType}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${productType}" ${selectedValue}/>
                                                      ${productType}
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
                            <script language="javascript">
                                $(document).ready(function() {
                                      const availabilityDate = $("#availabilityDate");
                                      if (availabilityDate) {
                                        availabilityDate.datepicker({
                                          maxDate: 0,
                                          dateFormat: "dd-mm-yy",
                                          showAnim: "slideDown",
                                          duration: "fast",
                                          changeMonth: true,
                                          changeYear: true
                                        });
                                      }
                                });
                            </script>
        </div>
    </s:layout-component>
</s:layout-render>
