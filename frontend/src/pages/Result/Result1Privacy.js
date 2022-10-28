import styled from "styled-components";

import "App.css";

const Main = styled.div`
  font-family: SCDream5;
  width: 100rem;
  margin: 0 auto;
  @media screen and (max-width: 768px) {
    width: 90%;
  }
`;

const Title = styled.div`
  font-size: 25px;
  font-family: SCDream9;
  margin: 15px auto;
  @media screen and (max-width: 768px) {
    font-size: 15px;
  }
`;

const Info = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  width: 1000px;
  border-left: 2px solid gray;
  border-top: 2px solid gray;
  font-size: 16px;
  > div {
    &:nth-child(2n + 1) {
      background-color: #b1b2ff;
    }
  }
  @media screen and (max-width: 768px) {
    width: 100%;
    grid-template-columns: repeat(2, 1fr);
    font-size: 12px;
  }
`;

const InfoColumn = styled.div`
  width: 250px;
  height: 40px;
  text-align: center;
  line-height: 40px;
  border-bottom: 2px solid gray;
  border-right: 2px solid gray;

  @media screen and (max-width: 768px) {
    width: 100%;
    height: 30px;
    line-height: 30px;
  }
`;

const Result1Privacy = () => {
  return (
    <Main>
      <Title>1. 응시자 개인 정보</Title>
      <Info>
        <InfoColumn>응시자 정보</InfoColumn>
        <InfoColumn>홍길동</InfoColumn>
        <InfoColumn>생년월일</InfoColumn>
        <InfoColumn>1999.12.29</InfoColumn>
        <InfoColumn>전화번호</InfoColumn>
        <InfoColumn>010-1234-5678</InfoColumn>
        <InfoColumn>응시 날짜</InfoColumn>
        <InfoColumn>2022.09.26</InfoColumn>
      </Info>
    </Main>
  );
};

export default Result1Privacy;
