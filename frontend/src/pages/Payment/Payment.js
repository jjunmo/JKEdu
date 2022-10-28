import { useState } from "react";
import styled from "styled-components";

import "App.css";

import ImPortPayment from "./ImPortPayment";
import FreeVersion from "./FreeVersion";
import PaymentInfo from "./PaymentInfo";

const Main = styled.div`
  width: 100%;
  height: calc(100% - 80px);
  display: flex;
  justify-content: center;
  font-family: SCDream5;
  font-size: 16px;
`;

const Content = styled.div`
  margin: 50px auto;
  width: 1000px;

  @media screen and (max-width: 768px) {
    width: 100%;
    margin: 0.75rem auto;
  }
`;

const Title = styled.h2`
  text-align: center;
  margin: 15px 0;
`;

const List = styled.div`
  box-sizing: border-box;
  width: 1000px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(5, 1fr);

  border-top: 1px solid #c9d6df;
  border-left: 1px solid #c9d6df;

  font-size: 2.5rem;

  @media screen and (max-width: 768px) {
    width: 90%;
    font-size: 1.25rem;
  }
`;

const Menu = styled.div`
  width: 200px;
  aspect-ratio: 1 / 1;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  border-bottom: 1px solid #c9d6df;
  border-right: 1px solid #c9d6df;

  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;

const MenuTitle = styled.h2`
  font-size: 3.5rem;
  margin: 1rem 0;

  @media screen and (max-width: 768px) {
    margin: 0.5rem 0;
    font-size: 1.75rem;
  }
  @media screen and (max-width: 500px) {
    margin: 0.5rem 0;
    font-size: 1.2rem;
  }
`;
const MenuContent = styled.div`
  font-size: 2rem;
  @media screen and (max-width: 768px) {
    font-size: 1rem;
  }
`;

const Payment = () => {
  const [payInfo, setPayInfo] = useState({
    firstItem: {
      productName: "1회 이용권",
      price: 10000,
    },
    secondItem: {
      productName: "10+1회 이용권",
      price: 100000,
    },
    thirdItem: {
      productName: "50+5회 이용권",
      price: 500000,
    },
  });

  return (
    <Main>
      <Content>
        <Title>Payment</Title>
        <List>
          <Menu>
            <MenuTitle>요금제 선택</MenuTitle>
          </Menu>
          <Menu>
            <MenuTitle>체험</MenuTitle>
            <MenuContent>1회 무료</MenuContent>
            <FreeVersion></FreeVersion>
          </Menu>
          <Menu>
            <MenuTitle>1회</MenuTitle>
            <MenuContent>10,000원</MenuContent>
            <ImPortPayment payInfo={payInfo.firstItem} />
          </Menu>
          <Menu>
            <MenuTitle>10 + 1회</MenuTitle>
            <MenuContent>100,000원</MenuContent>
            <ImPortPayment payInfo={payInfo.secondItem} />
          </Menu>
          <Menu>
            <MenuTitle>50 + 5회</MenuTitle>
            <MenuContent>500,000원</MenuContent>
            <ImPortPayment payInfo={payInfo.thirdItem} />
          </Menu>
        </List>
        <PaymentInfo />
      </Content>
    </Main>
  );
};

export default Payment;
