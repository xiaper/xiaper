package io.xiaper.rest.controller.v1;

import io.xiaper.jpa.constant.AvatarConsts;
import io.xiaper.jpa.model.Company;
import io.xiaper.jpa.model.Region;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.repository.CompanyRepository;
import io.xiaper.jpa.repository.RegionRepository;
import io.xiaper.jpa.repository.UserRepository;
import io.xiaper.jpa.util.JpaUtil;
import io.xiaper.jpa.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.security.Principal;
import java.util.*;


/**
 * 公司、分公司
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RegionRepository regionRepository;

    /**
     * 分页查询分公司
     *
     * @param principal
     * @param page
     * @param size
     */
    @GetMapping("/get")
    public JsonResult get(Principal principal,
                          @RequestParam(value = "page") int page,
                          @RequestParam(value = "size") int size,
                          @RequestParam(value = "client") String client) {

        JsonResult jsonResult = new JsonResult();

        //
        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                // 分页查询
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
                Page<Company> companyPage = companyRepository.findByUser(adminOptional.get(), pageable);

                // 返回结果
                jsonResult.setMessage("分页查询分公司成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(companyPage);

            } else {

                jsonResult.setMessage("分页查询分公司失败-管理员账号不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 创建分公司
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public JsonResult create(Principal principal, @RequestBody Map map) {

        String name = (String) map.get("name");
        String description = (String) map.get("description");
        List<String> regions =  (List<String>) map.get("regions");

        JsonResult jsonResult = new JsonResult();

        //
        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Company company = new Company();
                company.setCid(JpaUtil.randomId());
                company.setName(name);
                company.setAvatar(AvatarConsts.DEFAULT_SYSTEM_AVATAR_URL);
                company.setDescription(description);
                company.setUser(adminOptional.get());

                // TODO：更新region
                company.getRegions().clear();
                Iterator iterator = regions.iterator();
                while (iterator.hasNext()) {
                    String code = (String) iterator.next();
                    Optional<Region> regionOptional = regionRepository.findByCode(code);
                    if (regionOptional.isPresent()) {
                        company.getRegions().add(regionOptional.get());
                    }
                }

                companyRepository.save(company);

                // 返回结果
                jsonResult.setMessage("创建分公司成功");
                jsonResult.setStatus_code(200);
                jsonResult.setData(company);

            } else {

                jsonResult.setMessage("创建分公司失败-管理员账号不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }


        return jsonResult;
    }


    /**
     * 创建分公司
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(Principal principal, @RequestBody Map map) {

        String cid = (String) map.get("cid");
        String name = (String) map.get("name");
        String description = (String) map.get("description");
        List<String> regions =  (List<String>) map.get("regions");

        JsonResult jsonResult = new JsonResult();

        //
        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {

                Optional<Company> companyOptional = companyRepository.findByCid(cid);
                if (companyOptional.isPresent()) {

                    companyOptional.get().setName(name);
                    companyOptional.get().setDescription(description);

                    // TODO：更新region
                    companyOptional.get().getRegions().clear();
                    Iterator iterator = regions.iterator();
                    while (iterator.hasNext()) {
                        String code = (String) iterator.next();
                        Optional<Region> regionOptional = regionRepository.findByCode(code);
                        if (regionOptional.isPresent()) {
                            companyOptional.get().getRegions().add(regionOptional.get());
                        }
                    }

                    companyRepository.save(companyOptional.get());


                    // 返回结果
                    jsonResult.setMessage("更新分公司成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(companyOptional.get());

                } else {

                    jsonResult.setMessage("更新分公司失败-cid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(cid);
                }


            } else {

                jsonResult.setMessage("更新分公司失败-管理员账号不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


    /**
     * 删除分公司
     *
     * @param principal
     * @param map
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(Principal principal, @RequestBody Map map) {

        String cid = (String) map.get("cid");

        JsonResult jsonResult = new JsonResult();

        //
        if (principal != null) {

            //
            Optional<User> adminOptional = userRepository.findByUsername(principal.getName());
            if (adminOptional.isPresent()) {


                Optional<Company> companyOptional = companyRepository.findByCid(cid);
                if (companyOptional.isPresent()) {

                    // 删除
                    companyRepository.delete(companyOptional.get());

                    // 返回结果
                    jsonResult.setMessage("删除分公司成功");
                    jsonResult.setStatus_code(200);
                    jsonResult.setData(cid);

                } else {

                    jsonResult.setMessage("删除分公司失败-cid不存在");
                    jsonResult.setStatus_code(-3);
                    jsonResult.setData(cid);
                }

            } else {

                jsonResult.setMessage("删除分公司失败-管理员账号不存在");
                jsonResult.setStatus_code(-2);
                jsonResult.setData(false);
            }

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }



        return jsonResult;
    }




    /**
     * 搜索过滤工作组
     *
     * TODO: 增加搜索 Region 条件参数
     *
     * @param principal
     * @return
     */
    @GetMapping("/filter")
    public JsonResult filter(Principal principal,
                             @RequestParam(value = "page") int page,
                             @RequestParam(value = "size") int size,
                             //
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "client") String client) {

        logger.info(" name {}, client {}", name, client);

        JsonResult jsonResult = new JsonResult();

        if (principal != null) {

            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

            // 构建动态查询条件
            Specification specification = (Specification<Company>) (root, criteriaQuery, criteriaBuilder) -> {

                List<Predicate> predicateList = new ArrayList<>();

                predicateList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            };

            Page<Company> companyPage = companyRepository.findAll(specification, pageable);

            // 返回结果
            jsonResult.setMessage("搜索客服成功");
            jsonResult.setStatus_code(200);
            jsonResult.setData(companyPage);

        } else {

            jsonResult.setMessage("access token invalid");
            jsonResult.setStatus_code(-1);
            jsonResult.setData(false);
        }

        return jsonResult;
    }


}
