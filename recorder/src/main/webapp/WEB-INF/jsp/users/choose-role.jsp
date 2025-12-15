<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Choose Role">

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
                                <s:form action="/web/assign-role" method="post" name="searchForm" id="searchForm">
                                <input type="hidden" name="target" value="${actionBean.target}"/>
                                <input type="hidden" name="_eventName" value="save"/>
                                <input type="hidden" name="user" value="${actionBean.user.id}"/>

                                <table>
                                    <thead>
                                    <tr>
                                        <th colspan="2">
                                            <h4><fmt:message key="chooserolelabel"/> to assign to ${actionBean.user.fullName}</h4>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr valign="top">
                                        <td><fmt:message key="chooserolelabel"/></td>
                                        <td nowrap="nowrap">
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="userRole" id="userRole" style="background-color:#F0E68C"
                                                onchange="enableOthers(this)">
                                                   <s:option value="">
                                                   <fmt:message key="chooserolelabel"/></s:option>
                                                   <c:forEach items="${actionBean.roles}" var="role"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.roles !=null &&
                                                             actionBean.userRole.id == role.id}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${role.id}" ${selectedValue}/>
                                                      ${role.roleName}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                        </td>
                                    </tr>

                                    <tr valign="top">
                                         <td><fmt:message key="choosevendorlabel"/></td>
                                        <td nowrap="nowrap">
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="vendor" id="vendor" style="background-color:#F0E68C" disabled="disabled">
                                                   <s:option value="">
                                                   <fmt:message key="selectvendorlabel"/></s:option>
                                                   <c:forEach items="${actionBean.vendors}" var="vendor"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.vendors !=null &&
                                                             actionBean.vendor.id == vendor.id}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${vendor.id}" ${selectedValue}/>
                                                      ${vendor.name}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                        </td>
                                    </tr>
                                    <tr valign="top">
                                         <td><fmt:message key="choosemerchantlabel"/></td>
                                        <td nowrap="nowrap">
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="merchant" id="merchant" style="background-color:#F0E68C"  disabled="disabled">
                                                   <s:option value="">
                                                   <fmt:message key="selectmerchantlabel"/></s:option>
                                                   <c:forEach items="${actionBean.merchants}" var="merchant"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.merchants !=null &&
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
                                        <td>&nbsp;</td>
                                        <td align="right">
                                            <fmt:message key="submitlabel" var="submitlabel"/>
                                            <d:submit name="submit" class="small" value="${submitlabel}" id="login"/>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                </s:form>
                        </div>
                    </div>
                </div>
            </div>
            <script language = "javascript">
              var userRoles = '${actionBean.rolesAsJson}';

              // If userRoles is a JSON string, parse it
              if (typeof userRoles === "string") {
                userRoles = JSON.parse(userRoles);
              }

              // Now create Maps client-side
              const vendorSpecificMap = new Map(userRoles.map(role => [role.id, role.vendorSpecific]));
              const merchantSpecificMap = new Map(userRoles.map(role => [role.id, role.merchantSpecific]));
              const merchant = document.getElementById('merchant');
              const vendor = document.getElementById('vendor');
              function enableOthers(element) {
                  const id = element.options[element.selectedIndex].value;
                  if(vendorSpecificMap.get(id) == true){
                    vendor.disabled = false;
                  }else{
                    vendor.disabled = true;
                  }
                  if(merchantSpecificMap.get(id) == true){
                    merchant.disabled = false;
                  }else{
                    merchant.disabled = true;
                  }
              }
            </script>
        </div>
    </s:layout-component>
</s:layout-render>
