<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Edit Contract">

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

                    <security:protected-element name="manage-contracts">
                       <s:form action="/web/edit-contract" name="createForm" id="createForm" method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="save"/>
                         <input type="hidden" name="contract" id="contract" value="${actionBean.contract.id}"/>

                        <table width="100%">
                            <tr>
                                <td align="center" colspan="3">
                                    <fmt:message key="editcontractlabel"/> for '${actionBean.contract.contractor}'
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
                                        <d:text style="background-color:#F0E68C" name="contractor" value="${actionBean.contractor}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="startdatelabel"/> (dd-mm-yyyy)
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="startDate"
                                        value="${actionBean.startDate}" id="startDate" class="startDate"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="enddatelabel"/> (dd-mm-yyyy)
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" id="endDate"
                                        name="endDate" value="${actionBean.endDate}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="renewaldatelabel"/> (dd-mm-yyyy)
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" id="renewalDate"
                                        name="renewalDate" value="${actionBean.renewalDate}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="minpurchaselabel"/> (in ${actionBean.purchaseCurrency})
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="minPurchase" value="${actionBean.minPurchase}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="maxpurchaselabel"/> (in ${actionBean.purchaseCurrency})
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="maxPurchase" value="${actionBean.maxPurchase}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="contracttermlabel"/>
                                    </td>
                                    <td nowrap="nowrap">
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="contractTerm" id="contractTerm" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectcontracttermlabel"/></s:option>
                                                   <c:forEach items="${actionBean.contractTerms}" var="contractTerm"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.contractTerm !=null &&
                                                             actionBean.contractTerm == contractTerm}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${contractTerm}" ${selectedValue}/>
                                                      ${contractTerm}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                    </td>
                                </tr>
                                    <tr>
                                        <td><fmt:message key="productslabel"/></td>
                                        <td colspan="2">
                                          <c:forEach items="${actionBean.products}" var="product" varStatus="loopStatus">
                                                    <c:set var="selectedValue" value=""/>
                                                    <c:if test="${funcs:isProductSelected(product, actionBean.contract.products)}">
                                                        <c:set var="selectedValue" value="checked='checked'"/>
                                                    </c:if>
                                                    <input type="checkbox" name="productIds" id="${product.id}" value="${product.id}"
                                                                             ${selectedValue}/>${product.name}
                                          </c:forEach>
                                        </td>
                                   </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="uploadlabel"/> (PDF only)
                                    </td>
                                    <td>
                                        <d:file style="background-color:#F0E68C" name="fileBean"/>
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
                                      const startDate = $("#startDate");
                                      if (startDate) {
                                        startDate.datepicker({
                                          maxDate: 0,
                                          dateFormat: "dd-mm-yy",
                                          showAnim: "slideDown",
                                          duration: "fast",
                                          changeMonth: true,
                                          changeYear: true
                                        });
                                      }

                                      const endDate = $("#endDate");
                                      if (endDate) {
                                        endDate.datepicker({
                                          maxDate: 0,
                                          dateFormat: "dd-mm-yy",
                                          showAnim: "slideDown",
                                          duration: "fast",
                                          changeMonth: true,
                                          changeYear: true
                                        });
                                      }

                                      const renewalDate = $("#renewalDate");
                                      if (renewalDate) {
                                        renewalDate.datepicker({
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
