package com.dio.santander.banklineapi.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.santander.banklineapi.dto.CorrentistaNovo;
import com.dio.santander.banklineapi.model.Conta;
import com.dio.santander.banklineapi.model.Correntista;
import com.dio.santander.banklineapi.repository.CorrentistaRepository;

@Service
public class CorrentistaService {
	
	@Autowired
	private CorrentistaRepository repository;

	public void save(CorrentistaNovo novo) {
		Correntista corr = new Correntista();
		corr.setCpf(novo.getCpf());
		corr.setNome(novo.getNome());
		
		Conta conta = new Conta();
		conta.setSaldo(0.0);
		conta.setNumero(new Date().getTime());
		corr.setConta(conta);
		
		repository.save(corr);
	}
}
