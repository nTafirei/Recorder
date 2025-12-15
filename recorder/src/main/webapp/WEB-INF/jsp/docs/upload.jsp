<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Upload ID">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                    <s:errors/>
                    <strong><fmt:message key="uploadfor"/> ${actionBean.user.fullName}
                    </strong>
                        <s:form action="/web/upload" method="post" name="searchForm" id="searchForm"
                        enctype = "multipart/form-data">
                        <input type="hidden" name="target" value="${actionBean.target}"/>
                        <input type="hidden" name="_eventName" value="upload"/>
                         <input type="hidden" name="user" value="${actionBean.user.id}"/>
                        <table class="alternating">
                                <tr>
                                    <td>
                                        File
                                    </td>
                                    <td align="right">
                                        <input type="file" name="fileBean" id="fileBean" style="background-color:#F0E68C"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="doctype"/></td>
                                    <td>
                                            <c:set var="selectedValue" value=""/>
                                             <s:select name="docType" id="docType" style="background-color:#F0E68C">
                                                   <s:option value="">Select Document Type</s:option>
                                                   <c:forEach items="${actionBean.docTypes}" var="docType"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.docType !=null &&
                                                             docType == docType}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${docType}" ${selectedValue}/>
                                                      ${docType.type}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                        <td align="right">
                                            <fmt:message key="submitlabel" var="submitlabel"/>
                                            <d:submit name="submit" class="small" value="${submitlabel}" id="login"/>
                                        </td>
                                    </tr>
                        </table>
                      </s:form>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
