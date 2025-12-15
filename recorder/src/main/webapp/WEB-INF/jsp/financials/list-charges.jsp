<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="View Transaction Charge Rate">

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

                    <security:protected-element name="view-charge-rates">
                    <s:errors/>
                        <c:if test="${empty actionBean.chargeRates}">
                            <strong><fmt:message key="nochargeratesfound"/> for ${actionBean.searchDates}</strong>
                        </c:if>
                        <c:if test="${!empty actionBean.chargeRates}">
                            <strong>${actionBean.chargeRatesSize} <fmt:message key="chargeratesfound"/> for ${actionBean.searchDates}</strong>
                        </c:if>
                       <s:form action="/web/charge-rates" name="createForm" id="createForm" method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="search"/>
                            <table width="100%">
                                 <tr>
                                    <td>
                                        <security:protected-element name="view-system-wallet-account">
                                          <d:link
                                              href="/web/account-report?account=${actionBean.systemWalletAccount.id}">
                                              <fmt:message key="systemaccountlabel"/>
                                           </d:link>
                                       </security:protected-element>
                                        <security:protected-element name="view-system-commissions-account">
                                          | <d:link
                                              href="/web/account-report?account=${actionBean.commissionsAccount.id}">
                                              <fmt:message key="systemcommisionsaccountlabel"/>
                                           </d:link>
                                       </security:protected-element>
                                      <security:protected-element name="view-tax-authority-account">
                                          | <d:link
                                              href="/web/account-report?account=${actionBean.taxAccount.id}">
                                              <fmt:message key="taxauthaccountlabel"/>
                                           </d:link>
                                      </security:protected-element>
                                      <security:protected-element name="view-exchange-rates">
                                           | <d:link
                                              href="/web/rates">
                                              <fmt:message key="rateslabel"/>
                                           </d:link>
                                       </security:protected-element>
                                      <security:protected-element name="view-charge-rates">
                                           | <d:link
                                              href="/web/charge-rates">
                                              <fmt:message key="chargerateslabel"/>
                                           </d:link>
                                       </security:protected-element>
                                      <security:protected-element name="view-loyalty-rates">
                                           | <d:link
                                              href="/web/loyalty-rates">
                                              <fmt:message key="loyaltyrateslabel"/>
                                           </d:link>
                                       </security:protected-element>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="startdatelabel"/> (dd-mm-yyyy)
                                        <d:text style="background-color:#F0E68C" name="startDate" id="startDate" class="startDate"/>
                                    </td>
                                    <td>
                                        <fmt:message key="enddatelabel"/> (dd-mm-yyyy)
                                        <d:text style="background-color:#F0E68C" name="endDate" id="endDate" class="endDate"/>
                                    </td>
                                    <td>
                                            <fmt:message key="typelabel"/>
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="chargeType" id="merchant" style="background-color:#F0E68C">
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
                                    <td align="right" valign="bottom">
                                        <fmt:message key="searchnowlabel" var="searchnowlabel"/>
                                        <d:submit class="small" name="search" value="${searchnowlabel}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4" align="right">
                                           <d:link
                                              href="/web/charge-rates?activeStatus=ACTIVE">
                                              <fmt:message key="activelabel"/>
                                           </d:link>
                                           | <d:link
                                              href="/web/charge-rates?activeStatus=NOT_ACTIVE">
                                              <fmt:message key="notactivelabel"/>
                                           </d:link>
                                    </td>
                                <tr>
                            </table>
                         </s:form>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.chargeRates}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="namelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="transactiontypelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="chargetypelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="currencylabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="amountlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="rangestartlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="rangeendlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="datelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="statuslabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="statuslabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.chargeRates}" var="chargeRate"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                      <td>
                                          ${chargeRate.name}
                                      </td>
                                      <td>
                                          ${chargeRate.transactionType.type}
                                      </td>
                                      <td>
                                          ${chargeRate.chargeType.type}
                                      </td>
                                      <td>
                                          ${actionBean.purchaseCurrency}
                                      </td>
                                      <td>
                                          ${chargeRate.amount}
                                          <c:if test="${chargeRate.chargeType == 'PERCENT'}">
                                                %
                                          </c:if>
                                      </td>
                                      <td>
                                          ${chargeRate.rangeStart}
                                      </td>
                                      <td>
                                          ${chargeRate.rangeEnd}
                                      </td>
                                      <td>
                                          ${chargeRate.formattedDay}
                                      </td>
                                      <td>
                                          ${chargeRate.activeStatus.status}
                                      </td>
                                        <td align="right">
                                        <c:if test="${chargeRate.canBeEdited}">
                                            <security:protected-element name="manage-charge-rates">
                                                         <d:link
                                                                     href="/web/edit-charge-rate?chargeRate=${chargeRate.id}">
                                                                     <fmt:message key="editlabel"/>
                                                         </d:link>
                                                          <c:if test="${chargeRate.activeStatus == 'ACTIVE'}">
                                                                     <d:link
                                                                         href="/web/charge-rate?chargeRate=${chargeRate.id}&_eventName=change&activeStatus=NOT_ACTIVE">
                                                                         <fmt:message key="disablelabel"/>
                                                                         </d:link>
                                                          </c:if>
                                                          <c:if test="${chargeRate.activeStatus == 'NOT_ACTIVE'}">
                                                                     <d:link
                                                                         href="/web/charge-rate?chargeRate=${chargeRate.id}&_eventName=change&activeStatus=ACTIVE">
                                                                         <fmt:message key="enablelabel"/>
                                                                         </d:link>
                                                          </c:if>
                                            </security:protected-element>
                                        </c:if>
                                      </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </c:if>
                        </table>
                    </security:protected-element>
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
                                });
                            </script>
        </div>
    </s:layout-component>
</s:layout-render>
