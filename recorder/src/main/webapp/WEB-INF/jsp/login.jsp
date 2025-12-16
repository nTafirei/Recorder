<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Login">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">


        <!-- Main -->
        <div id="main-wrapper">
            <div class="container">
                <div class="row 200%">
                    <div class="8u important(collapse)">
                        <div id="content">

                            <div class="row">
                                <section class="6u 12u(narrower)">
                                    <div class="box post">
                                        <div class="inner">
                                            <s:errors/>
                                        </div>
                                    </div>
                                </section>
                            </div>
                            <!-- Content -->
                            <article>
                                <s:form action="/web/login" method="post" name="loginForm" id="loginForm">
                                <input type="hidden" name="target" value="${actionBean.target}"/>
                                <input type="hidden" name="_eventName" value="login"/>

                                <table>
                                    <thead>
                                    <tr>
                                        <th colspan="2">
                                            <h4><fmt:message key="dologinlabel"/></h4>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr valign="top">
                                        <td nowrap="nowrap"><fmt:message key="mobilephonelabel" var="usernameLabel"/></td>
                                        <td nowrap="nowrap">
                                            <strong>${usernameLabel}</strong>
                                            <d:text style="background-color:#F0E68C" name="username" id="username"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="passwordlabel" var="passwordLabel"/></td>
                                        <td>
                                            <strong>${passwordLabel}</strong>
                                            <d:password style="background-color:#F0E68C" name="password" id="password"/>
                                        </td>
                                    </tr>

                                    <c:if test="${actionBean.isShowDemoCredentials}">
                                        <tr>
                                            <td>
                                                <strong>Demo User Credentials</strong>
                                            </td>
                                            <td>
                                                username:<strong>${actionBean.demoUser}</strong>
                                                    <br/>
                                                password:<strong>test</strong>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <strong>Demo Admin Credentials</strong>
                                            </td>
                                            <td>
                                                username:<strong>${actionBean.demoAdmin}</strong>
                                                    <br/>
                                                password:<strong>test</strong>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right">
                                            <fmt:message key="submitlabel" var="submitlabel"/>
                                            <d:submit name="submit" class="small" value="${submitlabel}" id="login"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right">
                                            <br/>
                                              <d:link
                                                   href="/web/register2"><fmt:message
                                                   key="signuplabel"/></d:link>
                                            | <d:link
                                                   href="/web/forgot-password"><fmt:message
                                                   key="forgotpasswordlabel"/></d:link>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                </s:form>
                            </article>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
