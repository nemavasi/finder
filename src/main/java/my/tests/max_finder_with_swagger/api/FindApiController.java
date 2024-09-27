package my.tests.max_finder_with_swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import my.tests.max_finder_with_swagger.service.FindService;
import my.tests.max_finder_with_swagger.service.WrongParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-09-27T08:22:56.704988760Z[GMT]")
@RestController
public class FindApiController implements FindApi {

    private static final Logger log = LoggerFactory.getLogger(FindApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final FindService findService;

    @Autowired
    public FindApiController(ObjectMapper objectMapper, HttpServletRequest request, FindService findService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.findService = findService;
    }

    public ResponseEntity<String> getNMaxNumber
            (@NotNull @Parameter(in = ParameterIn.QUERY, description = "Max number from top", required = true, schema = @Schema(defaultValue = "1", minimum = "1"))
             @Valid @RequestParam(value = "n", required = true, defaultValue = "1") Integer n,
             @NotNull @Parameter(in = ParameterIn.QUERY, description = "Path to local file", required = true, schema = @Schema())
             @Valid @RequestParam(value = "p", required = true) String p
            ) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Integer result = findService.findNMaxFormFile(p, n);
                return new ResponseEntity<>(String.valueOf(result), HttpStatus.OK);
            } catch (WrongParameterException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
