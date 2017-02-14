<%--
  User: pavlo
  Date: 11.02.2017
  Time: 19:23
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Header -->
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">My Categories</h1>
    </div>
</div>

<!-- Table with the list of categories -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <%--<div class="panel-body">--%>

                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="text-center">ID</th>
                        <th class="text-center">Name</th>
                        <th class="text-center">Edit</th>
                        <th class="text-center">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${categories}" var="category">
                        <tr class="gradeA">
                            <td><c:out value="${category.id}"></c:out></td>
                            <td><c:out value="${category.name}"></c:out></td>
                            <td class="text-center"><a href="#"><i class="fa fa-edit fa-fw"></i>edit</a></td>
                            <td class="text-center"><a href="#"><i class="fa fa-trash-o fa-fw"></i>delete</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            <%--</div>--%>
        </div>
    </div>
</div>