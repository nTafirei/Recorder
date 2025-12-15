<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Answer Questions">

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
                                <s:form action="/web/forgot-password" method="post" name="loginForm" id="loginForm">
                                <input type="hidden" name="_eventName" value="send"/>
                                <input type="hidden" name="mobileNumber" value="${actionBean.mobileNumber}"/>

                                <table>
                                    <thead>
                                    <tr>
                                        <th colspan="2">
                                            <h4><fmt:message key="resetpasswordlabel"/></h4>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                       <tr valign="top">
                                        <td>
                                            ${actionBean.securityQuestion1}
                                            <input type = "text" style="background-color:#F0E68C"
                                            name="answer1" value="${actionBean.answer1}"/>
                                        </td>
                                        <td>
                                            ${actionBean.securityQuestion2}
                                            <input type = "text" style="background-color:#F0E68C"
                                            name="answer2" value="${actionBean.answer2}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            ${actionBean.securityQuestion3}
                                            <input type = "text" style="background-color:#F0E68C"
                                            name="answer3" value="${actionBean.answer3}"/>
                                        </td>
                                            <td align="right">
                                                <fmt:message key="submitlabel" var="submitlabel"/>
                                                <d:submit name="submit" class="small" value="${submitlabel}" id="login"/>
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
