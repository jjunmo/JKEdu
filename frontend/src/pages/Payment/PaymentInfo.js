import styled from "styled-components";

import "App.css";

const Main = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
`;

const Content = styled.div`
  margin: 50px auto;
  width: 1000px;
`;

const Info = styled.div`
  display: grid;
  grid-template-columns: repeat(5, 1fr);

  @media screen and (max-width: 768px) {
    width: 90%;
    margin: 0.75rem auto;
  }
`;

const Title = styled.h2`
  text-align: center;
  margin: 0;
  margin: 15px 0;
`;

const InfoHeader = styled.div`
  box-sizing: border-box;
  width: 20rem;
  aspect-ratio: 2 / 1;

  border: 1px solid #c9d6df;
  display: flex;
  align-items: center;
  justify-content: center;

  background-color: #f0f5f9;
  border-right: none;
  text-align: center;

  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;

const InfoContent = styled(InfoHeader)`
  background-color: white;
  color: black;
`;

const InfoContentEnd = styled(InfoHeader)`
  background-color: white;
  border-right: 1px solid #c9d6df;
`;

const ColorSpan = styled.span`
  color: ${(prop) => prop.color};
  font-size: 2.5rem;

  @media screen and (max-width: 768px) {
    font-size: 1.2rem;
  }
`;

const PaymentInfo = () => {
  return (
    <Main>
      <Content>
        <Title>이용권별 정보</Title>
        <Info>
          <InfoHeader></InfoHeader>
          <InfoContent>
            <ColorSpan color="black">체험</ColorSpan>
          </InfoContent>
          <InfoContent>
            <ColorSpan color="black">1회</ColorSpan>
          </InfoContent>
          <InfoContent>
            <ColorSpan color="black">10 + 1회</ColorSpan>
          </InfoContent>
          <InfoContentEnd>
            <ColorSpan color="black">50 + 5회</ColorSpan>
          </InfoContentEnd>
          <InfoHeader>
            <ColorSpan color="black">학생 관리</ColorSpan>
          </InfoHeader>
          <InfoContent>
            <ColorSpan color="red">X</ColorSpan>
          </InfoContent>
          <InfoContent>
            <ColorSpan color="red">X</ColorSpan>
          </InfoContent>
          <InfoContent>
            <ColorSpan color="black">100명</ColorSpan>
          </InfoContent>
          <InfoContentEnd>
            <ColorSpan color="black">무제한</ColorSpan>
          </InfoContentEnd>
          <InfoHeader>
            <ColorSpan color="black">추천 커리큘럼</ColorSpan>
          </InfoHeader>
          <InfoContent>
            <ColorSpan color="red">X</ColorSpan>
          </InfoContent>
          <InfoContent>
            <ColorSpan color="green">O</ColorSpan>
          </InfoContent>
          <InfoContent>
            <ColorSpan color="green">O</ColorSpan>
          </InfoContent>
          <InfoContentEnd>
            <ColorSpan color="green">O</ColorSpan>
          </InfoContentEnd>
          <InfoHeader>
            <ColorSpan color="black">자동 시간표</ColorSpan>
          </InfoHeader>
          <InfoContent>
            <ColorSpan color="red">X</ColorSpan>
          </InfoContent>
          <InfoContent>
            <ColorSpan color="red">X</ColorSpan>
          </InfoContent>
          <InfoContent>
            <ColorSpan color="green">O</ColorSpan>
          </InfoContent>
          <InfoContentEnd>
            <ColorSpan color="green">O</ColorSpan>
          </InfoContentEnd>
        </Info>
      </Content>
    </Main>
  );
};

export default PaymentInfo;
