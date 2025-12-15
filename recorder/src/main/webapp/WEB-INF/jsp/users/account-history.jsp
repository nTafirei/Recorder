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

                    <table class="alternating">
                        <c:if test="${empty actionBean.incomingPayments && empty actionBean.outGoingPayments}">
                            <fmt:message key="noaccounthistoryfound"/>
                            ${actionBean.user.firstName} ${actionBean.user.lastName}
                        </c:if>
             <thead>
                    <tr>
                            <td colspan="7">
                              <fmt:message key="accounthistoryforlabel"/>
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
                                         <fmt:message key="tomobilephonelabel"/>
                                     </td>
                                      <td>
                                          <fmt:message key="paidlabel"/>
                                      </td>
                                    <td>
                                        <fmt:message key="amountlabel"/>
                                    </td>

                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.incomingPayments}" var="payment"
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
                                                  ${payment.toUserMobileNumber}
                                         </td>
                                          <td>
                                                  ${payment.pickedUp}
                                                  <c:if test="${payment.pickedUp == 'YES'}">
                                                   ( ${payment.formattedDateLastUpdated} )
                                                  </c:if>
                                          </td>
                                        <td>
                                                USD ${payment.amount}
                                                <c:if test="${payment.pickedUp == 'NO'}">
                                                    <security:protected-element name="pay-users">
                                                    -
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
                                                 </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:forEach items="${actionBean.outGoingPayments}" var="payment"
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
                                                   ${payment.toUserMobileNumber}
                                          </td>
                                         <td>
                                                 ${payment.pickedUp}
                                                   <c:if test="${payment.pickedUp == 'YES'}">
                                                    ( ${payment.formattedDateLastUpdated} )
                                                   </c:if>
                                         </td>
                                        <td>
                                                USD ${payment.amount}
                                        </td>
                                    </tr>
                                </c:forEach>
                                 <thead>

                                <c:if test="${!empty actionBean.activities}">
                                <tr>
                                      <td colspan="8">
                                          <strong><fmt:message key="otheractivitieslabel"/></strong>
                                      </td>
                                </tr>

                                <tr>
                                    <td>
                                        <fmt:message key="datecreatedlabel"/>
                                    </td>
                                     <td>
                                         <fmt:message key="activitytypelabel"/>
                                     </td>
                                    <td colspan="5">
                                        <fmt:message key="activityvaluelabel"/>
                                    </td>
                                </tr>
                                 </thead>
                                <c:forEach items="${actionBean.activities}" var="activity"
                                            varStatus="loopStatus">
                                <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                     <td>
                                         ${activity.formattedDateCreated}
                                     </td>
                                      <td>
                                          ${activity.activityType}
                                      </td>
                                      <td colspan="3">
                                         ${activity.value}
                                     </td>
                                 </tr>
                                  </c:forEach>
                                  </c:if>
                                </tbody>
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
