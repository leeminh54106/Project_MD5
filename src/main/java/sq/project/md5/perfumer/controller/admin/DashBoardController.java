package sq.project.md5.perfumer.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sq.project.md5.perfumer.model.dto.resp.DashboardResponse;
import sq.project.md5.perfumer.model.dto.resp.DataResponse;
import sq.project.md5.perfumer.service.IDashboardService;
import sq.project.md5.perfumer.service.impl.OrderServiceImpl;

@RestController
@RequestMapping("/api.example.com/v1/admin/dashBoard")
@RequiredArgsConstructor
public class DashBoardController {
    private final IDashboardService dashboardService;
    @GetMapping
    public ResponseEntity<?> getDashBoard() {
        return new ResponseEntity<>(new DataResponse(dashboardService.getDashboard(), HttpStatus.OK), HttpStatus.OK);
    }
}
