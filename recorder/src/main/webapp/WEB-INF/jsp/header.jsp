<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<!-- Header -->
<div id="header-wrapper">
    <header id="header" class="container">

        <!-- Logo -->

        <br/><br/>
        <div id="logo">
            <h1>
                <d:link href="/web/inbox"><fmt:message key="appnamekey"/></d:link>
            </h1>
        </div>

        <!-- Nav -->
        <nav id="nav">
            <ul>
                    <c:if test="${actionBean.navSection == 'home'}">
                        <li class="current"><d:link href="/web/home"><fmt:message key="homemenulabel"/></d:link></li>
                    </c:if>
                    <c:if test="${actionBean.navSection != 'home'}">
                            <li ><d:link href="/web/home"><fmt:message key="homemenulabel"/></d:link></li>
                    </c:if>

                 <c:if test="${actionBean.isLoggedIn == false}">
                    <c:if test="${actionBean.navSection == 'register'}">
                        <li class="current"><d:link href="/web/pre-register"><fmt:message key="registermenu"/></d:link></li>
                    </c:if>
                    <c:if test="${actionBean.navSection != 'register'}">
                            <li ><d:link href="/web/pre-register"><fmt:message key="registermenu"/></d:link></li>
                    </c:if>
                </c:if>
                        <c:if test="${actionBean.navSection == 'tools'}">
                             <li class="current"><d:link href="/web/tools"><fmt:message key="toolslabel"/></d:link></li>
                         </c:if>
                         <c:if test="${actionBean.navSection != 'tools'}">
                             <li><d:link href="/web/tools"><fmt:message key="toolslabel"/></d:link></li>
                         </c:if>

                        <c:if test="${actionBean.navSection == 'products'}">
                             <li class="current"><d:link href="/web/products"><fmt:message key="productslabel"/></d:link></li>
                         </c:if>
                         <c:if test="${actionBean.navSection != 'products'}">
                             <li><d:link href="/web/products"><fmt:message key="productslabel"/></d:link></li>
                         </c:if>

                <c:if test="${actionBean.isLoggedIn == true}">
                    <c:if test="${actionBean.navSection == 'inbox'}">
                        <li class="current"><d:link href="/web/inbox"><fmt:message key="inboxmenulabel"/></d:link></li>
                    </c:if>
                    <c:if test="${actionBean.navSection != 'inbox'}">
                            <li ><d:link href="/web/inbox"><fmt:message key="inboxmenulabel"/></d:link></li>
                    </c:if>
                     <security:protected-element name="view-security-policies">
                        <c:if test="${actionBean.navSection == 'security'}">
                            <li class="current"><d:link href="/web/security"><fmt:message key="securitymenulabel"/></d:link></li>
                        </c:if>
                        <c:if test="${actionBean.navSection != 'security'}">
                                <li ><d:link href="/web/security"><fmt:message key="securitymenulabel"/></d:link></li>
                        </c:if>
                     </security:protected-element>

                     <security:protected-element name="view-report-list">
                        <c:if test="${actionBean.navSection == 'reports'}">
                            <li class="current"><d:link href="/web/reports"><fmt:message key="reportsmenulabel"/></d:link></li>
                        </c:if>
                        <c:if test="${actionBean.navSection != 'reports'}">
                                <li ><d:link href="/web/reports"><fmt:message key="reportsmenulabel"/></d:link></li>
                        </c:if>
                     </security:protected-element>

                    <security:protected-element name="view-financials-menu">
                         <c:if test="${actionBean.navSection == 'financials'}">
                             <li class="current"><d:link href="/web/financials"><fmt:message key="financialslabel"/></d:link></li>
                         </c:if>
                         <c:if test="${actionBean.navSection != 'financials'}">
                             <li><d:link href="/web/financials"><fmt:message key="financialslabel"/></d:link></li>
                         </c:if>
                    </security:protected-element>
                    <security:protected-element name="manage-merchants">
                         <c:if test="${actionBean.navSection == 'merchants'}">
                             <li class="current"><d:link href="/web/merchants"><fmt:message key="merchantslabel"/></d:link></li>
                         </c:if>
                         <c:if test="${actionBean.navSection != 'merchants'}">
                             <li><d:link href="/web/merchants"><fmt:message key="merchantslabel"/></d:link></li>
                         </c:if>
                    </security:protected-element>

                    <security:protected-element name="manage-vendors">
                         <c:if test="${actionBean.navSection == 'vendors'}">
                             <li class="current"><d:link href="/web/vendors"><fmt:message key="vendorslabel"/></d:link></li>
                         </c:if>
                         <c:if test="${actionBean.navSection != 'vendors'}">
                             <li><d:link href="/web/vendors"><fmt:message key="vendorslabel"/></d:link></li>
                         </c:if>
                    </security:protected-element>

                    <security:protected-element name="show-user-list">
                         <c:if test="${actionBean.navSection == 'users'}">
                             <li class="current"><d:link href="/web/users?activeStatus=ACTIVE"><fmt:message key="userslabel"/></d:link></li>
                         </c:if>
                         <c:if test="${actionBean.navSection != 'users'}">
                             <li><d:link href="/web/users?activeStatus=ACTIVE"><fmt:message key="userslabel"/></d:link></li>
                         </c:if>
                    </security:protected-element>

                    <security:protected-element name="generate-reports">
                         <c:if test="${actionBean.navSection == 'reports'}">
                             <li class="current"><d:link href="/web/reports"><fmt:message key="reportslabel"/></d:link></li>
                         </c:if>
                         <c:if test="${actionBean.navSection != 'reports'}">
                             <li><d:link href="/web/reports"><fmt:message key="reportslabel"/></d:link></li>
                         </c:if>
                    </security:protected-element>
                         <li><d:link href="/web/my-transaction-history/${actionBean.currentUser.id}">
                                <fmt:message key="myaccounthistorylabel"/>
                            </d:link></li>
                    <security:protected-element name="view-contracts">
                         <c:if test="${actionBean.navSection == 'contracts'}">
                             <li class="current"><d:link href="/web/contracts?activeStatus=ACTIVE"><fmt:message key="contractslabel"/></d:link></li>
                         </c:if>
                         <c:if test="${actionBean.navSection != 'contracts'}">
                             <li><d:link href="/web/contracts?activeStatus=ACTIVE"><fmt:message key="contractslabel"/></d:link></li>
                         </c:if>
                    </security:protected-element>
                         <c:if test="${actionBean.navSection == 'my-details'}">
                             <li class="current"><d:link href="/web/my-details"><fmt:message key="mydetailslabel"/></d:link></li>
                         </c:if>
                         <c:if test="${actionBean.navSection != 'my-details'}">
                             <li><d:link href="/web/my-details"><fmt:message key="mydetailslabel"/></d:link></li>
                         </c:if>
                </c:if>

                <c:if test="${actionBean.isLoggedIn == false}">
                     <c:if test="${actionBean.navSection == 'login'}">
                         <li class='current'><d:link href="/web/login"><fmt:message key="loginmenulabel"/></d:link></li>
                     </c:if>
                     <c:if test="${actionBean.navSection != 'login'}">
                             <li><d:link href="/web/login"><fmt:message key="loginmenulabel"/></d:link></li>
                     </c:if>
                </c:if>
                <c:if test="${actionBean.isLoggedIn == true}">
                    <li><d:link href="/web/logout"><fmt:message key="logoutmenulabel"/></d:link></li>
                </c:if>
            </ul>
        </nav>
    </header>
</div>
