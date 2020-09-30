package cz.jpalcut.pia.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller pro zpracování errorů
 */
@Controller
public class AppErrorController implements ErrorController {

    /**
     * Zobrazí stránku s příslušným http status error codem
     *
     * @param request požadavek na server
     * @return ModelAndView
     */
    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("error/error");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addObject("errorCode", "404 Not Found");
                model.addObject("errorMessage", "Požadovaný dokument nebyl nalezen.");
                return model;
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addObject("errorCode", "500 Internal Server Error");
                model.addObject("errorMessage", "Při zpracovávání požadavku došlo ke blíže nespecifikované chybě.");
                return model;
            } else if (statusCode == HttpStatus.NOT_IMPLEMENTED.value()) {
                model.addObject("errorCode", "501 Not Implemented");
                model.addObject("errorMessage", "Nebyla rozpoznána metoda požadavku, nebo server tuto metodu neovládá.");
                return model;
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                model.addObject("errorCode", "400 Bad Request");
                model.addObject("errorMessage", "Požadavek nemůže být vyřízen, poněvadž byl syntakticky nesprávně zapsán.");
                return model;
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                model.addObject("errorCode", "401 Unauthorized");
                model.addObject("errorMessage", "Je vyžadována autorizace, ale nebyla zatím provedena.");
                return model;
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addObject("errorCode", "403 Forbidden");
                model.addObject("errorMessage", "Požadavek byl legální, ale server odmítl odpovědět.");
                return model;
            } else if (statusCode == HttpStatus.REQUEST_TIMEOUT.value()) {
                model.addObject("errorCode", "408 Request Timeout");
                model.addObject("errorMessage", "Vypršel čas vyhrazený na zpracování požadavku.");
                return model;
            } else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
                model.addObject("errorCode", "408 Request Timeout");
                model.addObject("errorMessage", "Služba je dočasně nedostupná.");
                return model;
            }
        }
        model.addObject("errorCode", "ERROR chyba");
        model.addObject("errorMessage", "Nastala blíže nespecifikovaná chyba.");
        return model;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
