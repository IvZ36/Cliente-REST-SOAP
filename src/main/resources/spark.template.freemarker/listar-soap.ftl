<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Listar SOAPr</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css">
    <link rel="stylesheet" href="assets/css/Pretty-Search-Form.css">
</head>

<body id="page-top">
<div id="wrapper">
    <nav class="navbar navbar-dark align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0">
        <div class="container-fluid d-flex flex-column p-0">
            <a class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0" href="/inicio">
                <div class="sidebar-brand-icon rotate-n-15"><i class="fas fa-laugh-wink"></i></div>
                <div class="sidebar-brand-text mx-3"><span>Cliente Acortador</span></div>
            </a>
            <hr class="sidebar-divider my-0">
            <div class="text-center d-none d-md-inline"><button class="btn rounded-circle border-0" id="sidebarToggle" type="button"></button></div>
        </div>
    </nav>
    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content">
            <nav class="navbar navbar-light navbar-expand bg-white shadow mb-4 topbar static-top">
                <div class="container-fluid"><button class="btn btn-link d-md-none rounded-circle mr-3" id="sidebarToggleTop" type="button"><i class="fas fa-bars"></i></button>
                    <ul class="nav navbar-nav flex-nowrap ml-auto">
                        <li class="nav-item dropdown no-arrow mx-1" role="presentation">
                        <li class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="false" href="#">SOAP</a>
                            <div class="dropdown-menu dropdown-menu-right dropdown-list dropdown-menu-right animated--grow-in"
                                 role="menu">
                                <h6 class="dropdown-header">Metodo REST</h6>
                                <a class="d-flex align-items-center dropdown-item" href="#">
                                    <div class="mr-3">
                                        <div class="bg-primary icon-circle"><i class="fas fa-file-alt text-white"></i></div>
                                    </div>
                                    <div><span class="small text-gray-500">Usando REST</span>
                                        <p>Listar URLs</p>
                                    </div>
                                </a>
                                <a class="d-flex align-items-center dropdown-item" href="#">
                                    <div class="mr-3">
                                        <div class="bg-success icon-circle"><i class="fas fa-file-alt text-white"></i></div>
                                    </div>
                                    <div><span class="small text-gray-500">Usando REST</span>
                                        <p>Acortar URL</p>
                                    </div>
                                </a>
                            </div>
                        </li>
                        </li>
                        <li class="nav-item dropdown no-arrow mx-1" role="presentation">
                        <li class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="false" href="#">SOAP</a>
                            <div class="dropdown-menu dropdown-menu-right dropdown-list dropdown-menu-right animated--grow-in"
                                 role="menu">
                                <h6 class="dropdown-header">Metodo SOAP</h6>
                                <a class="d-flex align-items-center dropdown-item" href="#">
                                    <div class="mr-3">
                                        <div class="bg-primary icon-circle"><i class="fas fa-file-alt text-white"></i></div>
                                    </div>
                                    <div><span class="small text-gray-500">Usando SOAP</span>
                                        <p>Listar URLs</p>
                                    </div>
                                </a>
                                <a class="d-flex align-items-center dropdown-item" href="#">
                                    <div class="mr-3">
                                        <div class="bg-success icon-circle"><i class="fas fa-file-alt text-white"></i></div>
                                    </div>
                                    <div><span class="small text-gray-500">Usando SOAP</span>
                                        <p>Acortar URL</p>
                                    </div>
                                </a>
                            </div>
                        </li>
                        <div class="shadow dropdown-list dropdown-menu dropdown-menu-right" aria-labelledby="alertsDropdown"></div>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="col p-0">
                <div class="row">
                    <div class="col-12 p-0">
                        <div class="card col-6 rounded-0 bg-light p-0 mx-auto mt-2">
                            <div class="card-header">
                                <h5 class="card-title">
                                    Listar posts SOAP
                                </h5>
                            </div>
                            <div class="card-body">
                                <form action="/soap/listarurl" method="POST">
                                    <div class="form-group">
                                        <label for="usuario">Usuario</label>
                                        <input class="form-control rounded-0" type="text" name="user" placeholder="usuario" required>
                                    </div>
                                    <button type="submit" class="btn btn-outline-secondary my-2 my-sm-0">Listar URLs del usuario</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="bg-white sticky-footer">
                <div class="container my-auto">
                </div>
            </footer>
        </div><a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a></div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.bundle.min.js"></script>
    <script src="assets/js/bs-charts.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="assets/js/theme.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/react@16/umd/react.production.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/react-dom@16/umd/react-dom.production.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/styled-components@4/dist/styled-components.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@microlink/mql@latest/dist/mql.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@microlink/vanilla@latest/dist/microlink.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function (event) {
            microlink('.link-previews', { size: 'small' })
        })
    </script>
</body>

</html>