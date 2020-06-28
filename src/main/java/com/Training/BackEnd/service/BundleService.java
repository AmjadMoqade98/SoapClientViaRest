package com.Training.BackEnd.service;

import com.Training.BackEnd.Soap.SoapClient;
import com.Training.BackEnd.GlobalVariables;
import com.Training.BackEnd.dao.BundleDao;
import com.Training.BackEnd.dto.BundleRequestDto;
import com.Training.BackEnd.dto.BundleResponseDto;
import com.Training.BackEnd.repository.BundleRepository;
import com.Training.BackEnd.wsdl.BundleSoap;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BundleService {

    public static Queue<BundleRequestDto> bundlesContainer = new LinkedList<>();
    @Autowired
    BundleRepository bundleRepository;
    @Autowired
    SoapClient soapClient;

    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public List<BundleResponseDto> getBundles() {
        List<BundleDao> bundlesDao = new ArrayList<>();
        bundleRepository.findAll().forEach(bundlesDao::add);
        System.out.println(bundlesDao.size());
        List<BundleResponseDto> bundlesDto = new ArrayList<>();
        for (BundleDao bundleDao : bundlesDao) {
            bundlesDto.add(DaoToResponseDto(bundleDao));
        }
        return bundlesDto;
    }

    public BundleResponseDto getBundle(int id) {
        return DaoToResponseDto(bundleRepository.findOne(id));
    }

    public void addBundle(BundleRequestDto bundleDto) {
        if (GlobalVariables.AerospikeInitial) {
            configureBundleId();
        }
        bundleRepository.save(RequestDtoToDao(bundleDto));
        GlobalVariables.bundleId++;
    }

    public void deleteBundle(int id) {
        bundleRepository.delete(id);
    }

    public void deleteBundles() {
        bundleRepository.deleteAll();
    }

    public void provisionBundle(int id) {
        BundleDao bundleDao = bundleRepository.findOne(id);
        BundleSoap bundleSoap = DaoToSoap(bundleDao);
        soapClient.addBundlesSoap(bundleSoap);
    }

    private BundleDao RequestDtoToDao(BundleRequestDto bundleDto) {
        ModelMapper modelMapper = new ModelMapper();
        BundleDao bundle = modelMapper.map(bundleDto, BundleDao.class);
        bundle.setActivateDate(getCurrentDate());
        bundle.setEndDate(getEndDate(bundle.getPeriod()));
        bundle.setId(GlobalVariables.bundleId);
        return bundle;
    }

    private BundleResponseDto DaoToResponseDto(BundleDao bundle) {
        ModelMapper modelMapper = new ModelMapper();
        BundleResponseDto bundleDto = modelMapper.map(bundle, BundleResponseDto.class);
        return bundleDto;
    }

    private BundleSoap DaoToSoap(BundleDao bundleDao) {
        ModelMapper modelMapper = new ModelMapper();
        BundleSoap bundleSoap = modelMapper.map(bundleDao, BundleSoap.class);
        return bundleSoap;
    }

    /*
    method that turn on the AutoIncrement property for the bundle id
     */
    private void configureBundleId() {
        List<BundleDao> bundles = new ArrayList<>();
        bundleRepository.findAll().forEach(bundles::add);
        if (bundles.size() == 0) {
            GlobalVariables.bundleId = 0;
        } else {
            GlobalVariables.bundleId = bundles.stream().mapToInt(v -> v.getId()).max().getAsInt() + 1;
        }
        GlobalVariables.AerospikeInitial = false;
    }

    public String getCurrentDate() {
        return simpleDateFormat.format(new Date());
    }

    public String getEndDate(int period) {
        return simpleDateFormat.format(DateUtils.addMonths(new Date(), period));
    }
}
