<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Activity Report">

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
                        <c:if test="${empty actionBean.activities}">
                            <strong><fmt:message key="noactivitiesfound"/> for ${actionBean.account.accountName}</strong>
                        </c:if>
                        <c:if test="${!empty actionBean.activities}">
                            <strong>${actionBean.activityCount} <fmt:message key="activitiesfound"/> for ${actionBean.account.accountName}</strong>
                        </c:if>
                        <table>
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
                                    </td>
                                </tr>
                             </table>
                       <s:form action="/web/account-report" name="createForm" id="createForm" method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="search"/>
                            <input type="hidden" name="account" id="account" value="${actionBean.account.id}"/>
                            <table width="100%">
                                <tr>
                                    <td>

                                                <br/>
                                                <c:forEach items="${actionBean.page.pageNumbers}" var="pageItem"
                                                                                          varStatus="loopStatus">
                                                  <d:link href="/web/account-report?account=${actionBean.account.id}&currPage=${pageItem}">${pageItem}</d:link> |
                                                </c:forEach>
                                    </td>
                                    <td>
                                        <fmt:message key="startdatelabel"/> (dd-mm-yyyy)
                                        <d:text style="background-color:#F0E68C" name="startDate" id="startDate" class="startDate"/>
                                    </td>
                                    <td>
                                        <fmt:message key="enddatelabel"/> (dd-mm-yyyy)
                                        <d:text style="background-color:#F0E68C" name="endDate" id="endDate" class="endDate"/>
                                    </td>
                                    <td align="right" valign="bottom">
                                        <fmt:message key="searchnowlabel" var="searchnowlabel"/>
                                        <d:submit class="small" name="search" value="${searchnowlabel}"/>
                                    </td>
                                </tr>
                            </table>
                         </s:form>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.activities}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="datelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="actorlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="typelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="amountlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="thedetailslabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.activities}" var="activity"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                            ${activity.formattedDateCreated}
                                        </td>
                                        <td>
                                            ${activity.actor.fullName}
                                        </td>
                                        <td>
                                            ${activity.title}
                                        </td>
                                        <td>
                                            ${activity.activityType.label}
                                        </td>
                                        <td>
                                            ${activity.currency} ${activity.amount}
                                        </td>
                                        <td>
                                                <d:link href="/web/activity-details?activity=${activity.id}">
                                                    <fmt:message key="detailslabel"/>
                                                </d:link>
                                        </td>
                                    </tr>
                                </c:forEach>
                                    <tr>
                                        <td colspan="7" align="right">
                                                    <d:link target="_blank" href="/web/account-report?exportType=PDF&_eventName=export&currPage=${actionBean.page.currPage}&startDate=${actionBean.startDate}&endDate=${actionBean.endDate}&account=${actionBean.account.id}">
                                                        <fmt:message key="exportropdflabel"/>
                                                    </d:link>
                                                    | <d:link target="_blank" href="/web/account-report?exportType=EXCEL&_eventName=export&currPage=${actionBean.page.currPage}&startDate=${actionBean.startDate}&endDate=${actionBean.endDate}&account=${actionBean.account.id}">
                                                        <fmt:message key="exporttoexcellabel"/>
                                                    </d:link>
                                                    | <d:link target="_blank" href="/web/account-report?exportType=CSV&_eventName=export&currPage=${actionBean.page.currPage}&startDate=${actionBean.startDate}&endDate=${actionBean.endDate}&account=${actionBean.account.id}">
                                                        <fmt:message key="exporttotextlabel"/>
                                                    </d:link>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:if>
                        </table>
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
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
