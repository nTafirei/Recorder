<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Outstanding Payments">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <security:protected-element name="pay-users">
                        <c:if test="${empty actionBean.payments}">
                            <fmt:message key="nooutstandingpaymentsfound"/>
                            ${actionBean.user.firstName} ${actionBean.user.lastName}
                        </c:if>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.payments}">
                                <thead>
  <tr>
                             <td colspan="7">
                             <fmt:message key="nooutstandingpaymentsfor"/>
                               ${actionBean.user.fullName} (${actionBean.user.mobileNumber}).
                               <fmt:message key="currentbalancelabel"/> is
                               $${actionBean.user.account.availableBalance.amount} USD
                             </td>
                     </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="datecreatedlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="sentbylabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="frommobilephonelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="amountlabel"/>
                                    </td>
                                    <td align="right">
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.payments}" var="payment"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                                ${payment.formattedDateCreated}
                                        </td>
                                      <td>
                                                ${payment.fromUser.firstName} ${payment.fromUser.lastName}
                                        </td>
                                        <td>
                                                 ${payment.fromUser.mobileNumber}
                                        </td>
                                        <td>
                                                USD ${payment.amount}
                                        </td>
                                         <td align="right">
                                                <security:protected-element name="pay-users">
                                                    <d:link
                                                              href="/web/pay-user/${payment.id}">
                                                              <fmt:message key="paycashlabel"/>
                                                    </d:link>
                                                    |
                                                    <d:link
                                                              href="/web/pay-to-account/${payment.id}">
                                                              <fmt:message key="paytoaccountlabel"/>
                                                    </d:link>
                                                </security:protected-element>

                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </c:if>
                        </table>
                    </security:protected-element>
                    <security:when-no-content-displayed>
                        <d:link href="/web/security/help"><fmt:message
                                key="securityexceptionlink"/></d:link>
                    </security:when-no-content-displayed>

                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
